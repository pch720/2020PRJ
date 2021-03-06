package poly.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.dto.BoardDTO;
import poly.dto.GroupDTO;
import poly.service.IBoardService;
import poly.service.IGroupService;
import poly.util.CmmUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource(name= "GroupService")
    private IGroupService groupservice;

    @Resource(name = "BoardService")
    private IBoardService boardservice;
    /*내 그룹*/
    @RequestMapping(value = "MyGroup")
    public String MyGroup(HttpSession session, Model model) throws Exception {
        String name=CmmUtil.nvl((String)session.getAttribute("SS_USER_NAME"));
        GroupDTO gDTO = new GroupDTO();
        gDTO.setUserName(name);
        gDTO.setFunction("0");

        List<GroupDTO> gList = new ArrayList<>();

        gList=groupservice.getgg(gDTO);
        if(gList==null){
            gList = new ArrayList<GroupDTO>();
        }
        model.addAttribute("gList",gList);
        return "GG/MyGroup";
    }

    /*그룹명 중복확인*/
    @RequestMapping(value = "Gncheck", method = RequestMethod.POST)
    public @ResponseBody
    String eCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String Gname = CmmUtil.nvl(request.getParameter("Gname"));
        log.info(Gname);
        return groupservice.GnCheck(Gname);
    }

    /*그룹만들기*/
    @RequestMapping(value = "MakeGroup")
    public String MakeGroup(HttpServletRequest request, HttpSession session, Model model) throws Exception{
        String msg,url;
        String gname = CmmUtil.nvl(request.getParameter("gname"));
        String greet = CmmUtil.nvl(request.getParameter("greet"));
        String function = CmmUtil.nvl(request.getParameter("function"));
        String name = CmmUtil.nvl((String)session.getAttribute("SS_USER_NAME"));
        GroupDTO gDTO = new GroupDTO();
        gDTO.setGroupName(gname);
        gDTO.setGreeting(greet);
        gDTO.setFunction(function);
        gDTO.setUserName(name);
        gDTO.setAuth("1");
        gDTO.setCount("1");
        log.info(gDTO.getFunction()+"/"+gDTO.getGroupName()+"/"+gDTO.getGreeting()+"/"+gDTO.getUserName());
        /*그룹을만들고*/
        int res = groupservice.MakeGG(gDTO);
        log.info(res);
        log.info(gDTO);
        /*만든사람을 그룹장으로 넣음*/
        int res1 = groupservice.MkGG(gDTO);
        if (gDTO.getFunction().equals("0")) {
            if (res==1&&res1==1){
                msg = "새로운 그룹이 생성되었습니다.";
            }else {
                msg = "그룹 생성이 실패했습니다.";
            }
            url = "MyGroup.do";
        }else{
            if (res==1&&res1==1){
                msg = "새로운 목표가 생성되었습니다.";
            }else {
                msg = "목표 생성이 실패했습니다.";
            }
            url = "MyGoal.do";
        }

        model.addAttribute("msg",msg);
        model.addAttribute("url",url);
        return "/redirect";
    }

    /*내 목표*/
    @RequestMapping(value = "MyGoal")
    public String MyGoal(HttpSession session, Model model) throws Exception {
        String name=CmmUtil.nvl((String)session.getAttribute("SS_USER_NAME"));
        GroupDTO gDTO = new GroupDTO();
        gDTO.setUserName(name);
        gDTO.setFunction("1");

        List<GroupDTO> gList = new ArrayList<>();

        gList=groupservice.getgg(gDTO);
        if(gList==null){
            gList = new ArrayList<GroupDTO>();
        }
        model.addAttribute("gList",gList);
        return "GG/MyGoal";
    }
    /*그룹 및 목표 그만두기*/
    @RequestMapping(value = "Delgu")
    public String Delgu(HttpServletRequest request,Model model) throws Exception {
        String name=CmmUtil.nvl(request.getParameter("user"));
        String Gname = CmmUtil.nvl(request.getParameter("Gname"));
        String count = CmmUtil.nvl(request.getParameter("count"));
        String url,msg;
        String referer = CmmUtil.nvl(request.getHeader("Referer"));
        log.info(name+"/"+Gname);
        GroupDTO gDTO = new GroupDTO();
        gDTO.setUserName(name);
        gDTO.setGroupName(Gname);

        int c = Integer.parseInt(count) - 1;
        log.info(count+"/"+c);
        count = Integer.toString(c);
        log.info(count);
        gDTO.setCount(count);
        int res1 = groupservice.chcount(gDTO);
        int res = groupservice.Delgu(gDTO);
        if(res==1 && res1==1)
            msg="탈퇴 되었습니다.";
        else
            msg="탈퇴에 실패하였습니다.";
        url=referer;
        model.addAttribute("msg",msg);
        model.addAttribute("url",url);
        return "/redirect";
    }
    /*그룹 시작 화면*/
    @RequestMapping(value = "Group")
    public String Group(HttpServletRequest request,Model model,HttpSession session) throws Exception {
        String seq = request.getParameter("seq");
        String useq = (String)session.getAttribute("SS_USER_SEQ");
        log.info(seq);
        GroupDTO gDTO = new GroupDTO();
        gDTO = groupservice.getGroupInfo(seq);
        String gname = gDTO.getGroupName();
        List<GroupDTO> user = groupservice.users(gname);
        if(user==null){
            log.info("값없음");
            user = new ArrayList<>();
        }else {
            log.info("user : "+user.get(0).getUserName());
        }
        model.addAttribute("user",user);
        List<BoardDTO> reply = boardservice.getrep(seq);
        if(reply==null){
            log.info("값없음");
            reply = new ArrayList<>();
        }
        model.addAttribute("reply",reply);
        List<BoardDTO> bList = new ArrayList<>();
        bList=boardservice.getnotice(seq);
        if (bList==null)
            bList = new ArrayList<BoardDTO>();
        log.info(bList.size());
        int a=0,b=0,c=0;
        List<BoardDTO> bnList = new ArrayList<>();
        List<BoardDTO> bwList = new ArrayList<>();
        List<BoardDTO> bfList = new ArrayList<>();
        List<BoardDTO> Chat = new ArrayList<>();
        Chat = boardservice.getChat(seq);
        if (Chat==null)
            Chat = new ArrayList<>();
        model.addAttribute("Chat",Chat);
        for (BoardDTO boardDTO : bList) {
            switch (boardDTO.getNotice()) {
                case "1":
                    bnList.add(a++, boardDTO);
                    break;
                case "2":
                    bwList.add(b++, boardDTO);
                    break;
                case "3":
                    bfList.add(c++, boardDTO);
                    break;
            }
        }
        if(bnList==null)
            bnList = new ArrayList<BoardDTO>();
        if(bwList==null)
            bwList = new ArrayList<BoardDTO>();
        if(bfList==null)
            bfList = new ArrayList<BoardDTO>();
        model.addAttribute("bfList",bfList);
        model.addAttribute("bnList",bnList);
        model.addAttribute("bwList",bwList);
        model.addAttribute("gDTO",gDTO);
        BoardDTO bDTO = new BoardDTO();
        String[] like = new String[bnList.size()];
        bDTO.setUserName(useq);
        for (int i =0; i<bnList.size();i++) {
            bDTO.setBoardSeq(bnList.get(i).getBoardSeq());
            log.info(bDTO.getBoardSeq());
            like[i]=boardservice.CL(bDTO);
            log.info(like[i]);
        }
        model.addAttribute("like",like);
        return "/GG/Group";
    }

    /*함께하는 기능*/
    @RequestMapping(value = "join")
    public String join(HttpServletRequest request, Model model) throws Exception {
        String msg,url;
        String name = CmmUtil.nvl(request.getParameter("name"));
        String group = CmmUtil.nvl(request.getParameter("group"));
        String function = CmmUtil.nvl(request.getParameter("function"));
        String count = CmmUtil.nvl(request.getParameter("count"));
        log.info(count);
        GroupDTO gDTO = new GroupDTO() ;
        gDTO.setGroupName(group);
        gDTO.setUserName(name);
        gDTO.setFunction(function);
        gDTO.setAuth("0");
        log.info(name+"////"+group);
        /*이미 함께 하고 있는지 확인*/
        String result = groupservice.already(gDTO);
        log.info(result);
        if(result.equals("0")){
            /*함께하기*/
            int c = Integer.parseInt(count) + 1;
            log.info(count+"/"+c);
            count = Integer.toString(c);
            log.info(count);
            gDTO.setCount(count);
            int res1 = groupservice.chcount(gDTO);
            int res = groupservice.MkGG(gDTO);
            log.info(res);
            if (res == 1 &&res1 == 1)
                msg = group + "에서 함께하게 되었습니다.";
            else
                msg = "가입에 실패하였습니다.";
        }else
            msg = group+"에서 이미 함께하고 있습니다.";

        url = "main.do";
        model.addAttribute("msg",msg);
        model.addAttribute("url",url);
        return "/redirect";
    }
    /*할일삭제*/
    @RequestMapping(value = "/delGG", method = RequestMethod.POST)
    public @ResponseBody int delGG(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String seq = request.getParameter("seq");
            return groupservice.DelGG(seq);
    }
}