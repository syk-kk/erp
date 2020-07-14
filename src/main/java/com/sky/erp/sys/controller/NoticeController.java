package com.sky.erp.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.entity.Notice;
import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.service.INoticeService;
import com.sky.erp.sys.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 *  公告管理前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-08
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private INoticeService noticeService;

    @RequestMapping("loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo){
        Page<Notice> page = new Page<>(noticeVo.getPage(),noticeVo.getLimit());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        queryWrapper.like(StrUtil.isNotBlank(noticeVo.getContent()),"content",noticeVo.getContent());
        queryWrapper.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        queryWrapper.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());
        queryWrapper.orderByDesc("createtime");
        noticeService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());

    }

    @RequestMapping("addNotice")
    public ResultObj addNotice(Notice notice, HttpSession session){
        try {
            System.out.println(notice.toString());
            User user = (User)session.getAttribute("user");
            notice.setCreatetime(new Date());
            notice.setOpername(user.getName());
            noticeService.save(notice);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("updateNotice")
    public ResultObj updateNotice(Notice notice){
        try {
            notice.setCreatetime(new Date());
            noticeService.updateById(notice);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(NoticeVo noticeVo){
        try {
            noticeService.removeById(noticeVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }

    }

    @RequestMapping("deleteBatchNews")
    public ResultObj deleteBatchNews(NoticeVo noticeVo){
        try {
            noticeService.removeByIds(Arrays.asList(noticeVo.getIds()));
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

}
