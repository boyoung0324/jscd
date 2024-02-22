package com.jscd.app.admin.controller;

import com.jscd.app.admin.domain.Pageable;
import com.jscd.app.admin.domain.SearchCondition;
import com.jscd.app.admin.dto.MemberManageDto;
import com.jscd.app.admin.service.AdminService;
import com.jscd.app.admin.service.MemberManageService;
import com.jscd.app.member.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

	/*
	작성일:20231123
	작성자:김보영
	작성 기능:회원 정보 읽기/수정
	 */

@Controller
@RequestMapping("/adminManage/memberManage")
public class MemberManageController {

    @Autowired
    MemberManageService manageService;
    @Autowired
    AdminService adminService;

    @GetMapping("/list") //전체 회원 목록 페이지
    public String getList(SearchCondition sc, Model model) {

        try {
            //1. 총 데이터 개수를 페이징 클래스에 전달 -> 페이징 초기화
            int listCnt = manageService.getSearchResultCnt(sc);
            System.out.println("게시물 개수 listCnt = " + listCnt);
            Pageable pageable = new Pageable(sc, listCnt);
            //2. 페이징 클래스를 넘기고, 데이터가 담긴 list 반환
            List<MemberManageDto> list = manageService.getSearchPage(sc);
            //3. jsp에 전달
            model.addAttribute("sc", sc);
            model.addAttribute("list", list);
            model.addAttribute("page", pageable);

        } catch (Exception e) {
            //에러 발생 시, 에러 msg 모델에 전달, 관리자 홈으로 이동
            e.printStackTrace();
            model.addAttribute("msg", "LIST_ERR");
            return "redirect:/admin/home";
        }
        return "/admin/memberManage/adminMemberManageList";
    }


    @GetMapping("/{mebrNo}/info") //회원 상세보기 페이지
    public String infoRead(@PathVariable Integer mebrNo, Integer page, Model model) {

        try { //전달 받은 회원번호로 회원 select
            MemberManageDto memberDto = manageService.read(mebrNo);
            //jsp에 전달
            model.addAttribute("memberDto", memberDto);
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            //에러 발생 시, 에러 msg 모델에 전달, list 목록으로 돌아가기
            model.addAttribute("msg", "READ_ERR");
            return "redirect:/adminManage/memberManage/list";
        }

        return "/admin/memberManage/adminMemberManage";
    }


    //상세 페이지 수정
    @PatchMapping("/{mebrNo}/info")
    @ResponseBody
    public Map<String,String> modifyInfo(@PathVariable Integer mebrNo, Integer page, @RequestBody MemberDto memberDto) {
            Map<String,String> map = new HashMap<>();
        try {
            //입력 받은 dto를 update
            int result = manageService.modifyDetail(memberDto);
            if(memberDto.getOriginGrade().equals("관리자")){ //서비스로 뺄지 고민
                //기존 등급이 관리자라면, 관리자 테이블에서 삭제
                adminService.removeAdmin(memberDto.getId());
            }
            if(result != 1) throw new Exception("Modify Error");
            map.put("redirect","/adminManage/memberManage/" + mebrNo + "/info?page=" + page);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error","Modify Error");
            return map;
        }

    }


    @PatchMapping("/list") //메인 페이지 등급/상태 일괄 수정
    @ResponseBody
    public Map<String,String> mainModify(@RequestBody MemberDto memberDto, Integer page) {
        Map<String, String> map = new HashMap<>();
        Integer[]mebrNoArr = memberDto.getMebrNoArr();
        System.out.println("배열 = " + Arrays.toString(mebrNoArr));

        try {
            //전달받은 회원 번호 배열을 list에 담기
            List mebrNo = new ArrayList<>(mebrNoArr.length);
            for (int i = 0; i < mebrNoArr.length; i++) {
                mebrNo.add(mebrNoArr[i]);
            }
            //상태,등급,회원번호 update
            int result =  manageService.modify(memberDto.getStatus(), memberDto.getGrade(), mebrNo);

            if(result != mebrNo.size()) throw new Exception("Modify Error");

            map.put("redirect","/adminManage/memberManage/list?page=" + page);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error","Status Modify Error");
            return map;
        }
    }


}
