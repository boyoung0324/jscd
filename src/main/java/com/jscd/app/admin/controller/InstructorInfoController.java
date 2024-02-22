package com.jscd.app.admin.controller;

import com.jscd.app.admin.domain.Pageable;
import com.jscd.app.admin.domain.SearchCondition;
import com.jscd.app.admin.dto.InstructorInfoDto;
import com.jscd.app.admin.dto.InstructorMemberInfoDto;
import com.jscd.app.admin.service.InstructorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

	/*
	작성일:20231122
	작성자:김보영
	작성 기능:강사 정보 읽기/수정
	 */

@Controller
@RequestMapping("/adminManage/instructor")
public class InstructorInfoController {

    @Autowired
    InstructorInfoService infoService;

    //전체 목록 가져오기
    @GetMapping("/list")
    public String getList(SearchCondition sc, Model model, HttpSession session) {

        try {
            //쿼리스트링으로 들어온 객체 jsp에 뿌려주기(페이징,검색 관련 iv 담겨있다)
            model.addAttribute("sc", sc);
            //(1-1)총 게시물 개수를 받아와서
            int totalCnt = infoService.getSearchResultCnt(sc);

            //(1-2)페이징 처리 객체 매개변수로 넣어준다
            Pageable pageable = new Pageable(sc, totalCnt);
            //(1-3)jsp에서의 페이징 처리를 위해 모델로 넘겨준다
            model.addAttribute("page", pageable);

            //(2-1)강사 정보를 list에 받고,
            List<InstructorMemberInfoDto> list = infoService.getSearchPage(sc);
            //(2-2)jsp에 뿌려주기
            model.addAttribute("list", list);

        } catch (Exception e) {
            e.printStackTrace();
            //에러 존재 시, 메세지를 jsp에 넘기기
            model.addAttribute("msg", "LIST_ERR");
            return "redirect:/admin/home";
        }
        return "/admin/instructorManage/instructorInfoList";
    }

    //강사 상세 정보 읽기
    @GetMapping("/{mebrNo}/info")
    public String infoRead(@PathVariable Integer mebrNo, Integer page, Model model) {

        try {
            //쿼리스트링으로 넘어온 iscrNo로 강사 데이터를 select
            InstructorMemberInfoDto infoDto = infoService.read(mebrNo);

            //객체를 jsp에 넘겨주기
            model.addAttribute("infoDto", infoDto);
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "READ_ERR");
            return "redirect:/adminManage/instructor/list";
        }
        return "/admin/instructorManage/instructorInfo";
    }

    //상세페이지 수정
    @PatchMapping("/{mebrNo}/info")
    @ResponseBody
    public Map<String,String> modifyInfo(@PathVariable Integer mebrNo, Integer page, @RequestBody InstructorInfoDto instructorInfoDto) {
        Map<String, String> map = new HashMap<>();

        try{
            int result = infoService.modify(instructorInfoDto);
            if(result != 1) throw new Exception("Modify Error");

            map.put("redirect","/adminManage/instructor/" + mebrNo + "/info?page="+page);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("error","Modify Error");
            return map;
        }
    }


    //메인화면 수정
    @PatchMapping("/list")
    @ResponseBody
    public Map<String,String> statusModify(@RequestBody InstructorInfoDto infoDto, Integer page) {
        Map<String, String> map = new HashMap<>();
        Integer[]mebrNoArr = infoDto.getMebrNoArr();

        try{
            List mebrNo = new ArrayList<>(mebrNoArr.length);
            for (int i = 0; i < mebrNoArr.length; i++) {
                mebrNo.add(mebrNoArr[i]);
            }
            int result = infoService.modifyStatus(infoDto.getStatus(),mebrNo);
            if(result != mebrNo.size()) throw new Exception("Status Modify Error");

            map.put("redirect","/adminManage/instructor/list?page="+page);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("error","Status Modify Error");
            return map;
        }
    }



}

