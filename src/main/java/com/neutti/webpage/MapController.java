package com.neutti.webpage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping(value="map")
public class MapController {

    @RequestMapping(value="index.html", method = {RequestMethod.GET})
    public String evts(Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("TYPE1","EVTS");
        model.addAttribute("SYSTEM_TITLE","GIS Total Solution : KGeoSTD");
        model.addAttribute("SYSTEM_MAINTITLE","KGeoSTD");
        model.addAttribute("SYSTEM_SUBTITLE","GIS Total Solution");
        return "map/index";
    }

    @RequestMapping(value="sgv-system.html", method = {RequestMethod.GET})
    public String sgvs(Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("TYPE1","SGVS");
        model.addAttribute("SYSTEM_TITLE","스마트위병소 영상관제 시스템 : 해병대교육훈련단");
        model.addAttribute("SYSTEM_MAINTITLE","해병대교육훈련단");
        model.addAttribute("SYSTEM_SUBTITLE","ROK Marine Corps Education & Training Group");
        return "map/index";
    }
}
