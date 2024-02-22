package com.jscd.app.admin.controller;


import com.jscd.app.admin.domain.Pageable;
import com.jscd.app.admin.domain.SearchCondition;
import com.jscd.app.admin.dto.StdManageDto;
import com.jscd.app.admin.dto.StdMemberManageDto;
import com.jscd.app.admin.service.StdManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

	/*
	작성일:20231123
	작성자:김보영
	작성 기능:학생 정보 읽기/수정
	 */

@Controller
@RequestMapping("/adminManage/stdManage")
public class StdManageController {
    @Autowired
    StdManageService stdService;

    @GetMapping("/list") //전체 학생 목록
    public String getList(SearchCondition sc, Model model) {

        try {
            //총 학생 수 페이징 클래스에 넘기기(페이징에 필요한 iv 초기화)
            int totalCnt = stdService.getSearchResultCnt(sc);
            Pageable pageable = new Pageable(sc, totalCnt);
            //navi에 사용하기 위해 모델에 전달
            model.addAttribute("totalCnt", totalCnt);

            //학생 목록 list 셀렉
            List<StdMemberManageDto> list = stdService.getSearchPage(sc);
            //jsp에 전달
            model.addAttribute("list", list);
            model.addAttribute("sc", sc);
            model.addAttribute("page", pageable);

        } catch (Exception e) {
            //에러 발생 시, 에러 msg 전달, 관리자 홈으로 이동
            e.printStackTrace();
            model.addAttribute("msg", "LIST_ERR");
            return "redirect:/admin/home";
        }
        return "/admin/stdManage/stdManageList";
    }


    @GetMapping("/{mebrNo}/info") //상세 페이지
    public String infoRead(@PathVariable Integer mebrNo, Integer page, Model model) {

        try {
            //전달받은 회원번호로 학생 조회
            StdMemberManageDto stdDto = stdService.read(mebrNo);
            //jsp에 전달
            model.addAttribute("stdDto", stdDto);
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            //에러 발생 시, 에러 msg 전달, 학생 목록으로 이동
            model.addAttribute("msg", "READ_ERR");
            return "redirect:/adminManage/stdManage/list";
        }
        return "/admin/stdManage/stdManage";
    }



    //상세페이지
    @PatchMapping("/{mebrNo}/info")
    @ResponseBody
    public Map<String,String> infoModify(@PathVariable Integer mebrNo,Integer page, @RequestBody StdManageDto stdDto) {
        Map<String,String> map = new HashMap<>();
        try {
            int result = stdService.modify(stdDto);

            if(result != 1) throw new Exception("Modify Error");

            map.put("redirect","/adminManage/stdManage/" + mebrNo + "/info?page="+page);
            return map;

            //성공 msg 전달
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error","Modify Error");
            return map;
        }
    }

    //메인 페이지 상태 일괄 수정
     @PatchMapping("/list")
     @ResponseBody
    public Map<String,String> statusModify(@RequestBody StdManageDto stdDto, Integer page) {
        Map<String, String> map = new HashMap<>();
        Integer[]mebrNoArr = stdDto.getMebrNoArr();
        try {
            //전달 받은 회원 번호 배열 list에 담기
            List mebrNo = new ArrayList(mebrNoArr.length);
            for (int i = 0; i < mebrNoArr.length; i++) {
                mebrNo.add(mebrNoArr[i]);
            }
            //상태와 배열 전달
            int result = stdService.modifyStatus(stdDto.getStatus(), mebrNo);
            System.out.println("수정 수 = " + result);

            if(result != mebrNo.size()) throw new Exception("Status Modify Error");

            map.put("redirect","/adminManage/stdManage/list?page="+page);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            map.put("error","Status Modify Error");
            return map;
        }
    }

    @DeleteMapping("/{mebrNo}/info") //상세보기 화면에서 삭제
    @ResponseBody
    public Map<String,String> stdDelete(@PathVariable Integer mebrNo, Integer page, Model model) {
        Map<String,String> map = new HashMap<>();
        try {
            //전달받은 회원번호로 학생 삭제
            int result = stdService.remove(mebrNo);
            if(result != 1) throw new Exception("DELETE ERROR");

            map.put("redirect","/adminManage/stdManage/list?page=" + page);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            map.put("error","Modify Error");
            return map;
        }
    }

    //메인화면에서 삭제
     @DeleteMapping("/list")
     @ResponseBody
    public Map<String,String> stdDeleteMain(@RequestBody StdManageDto stdDto) {
         Map<String, String> map = new HashMap<>();
         Integer[]mebrNoArr = stdDto.getMebrNoArr();
        try {
            //전달받은 회원번호 배열 list에 담기
            List mebrNo = new ArrayList(mebrNoArr.length);
            for (int i = 0; i < mebrNoArr.length; i++) {
                mebrNo.add(mebrNoArr[i]);
            }
            //회원 배열 전달
            int result = stdService.removeMain(mebrNo);
            System.out.println("삭제 수 = " + result);
            System.out.println("mebrNo.size() 삭제 배열 = " + mebrNo.size());
            if(result != 1) throw new Exception("Delete Error");

            map.put("redirect","/adminManage/stdManage/list");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error","Delete Error");
            return map;

        }
    }


}
