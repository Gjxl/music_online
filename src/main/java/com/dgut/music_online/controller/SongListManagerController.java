package com.dgut.music_online.controller;

import com.dgut.music_online.domain.Detail;
import com.dgut.music_online.domain.SongList;
import com.dgut.music_online.service.SongListManagerService;
import com.dgut.music_online.tool.FileOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@Api(tags = "对歌单操作的相关接口")
public class SongListManagerController {

    @Autowired
    private Detail detail;

    @Autowired
    private SongList songList;

    @Autowired
    private SongListManagerService songListManagerService;

    @ApiOperation("获取所有歌单信息")
    @GetMapping(value = "/songList")
    public Detail getAllSongLists() {
        List<SongList> songLists = songListManagerService.getAllSongLists();
        Map<Object, Object> map = new HashMap<>();
        map.put("songLists", songLists);
        detail.setDetail(map);
        return detail;
    }

    @ApiOperation("根据歌单ID获取歌单信息")
    @GetMapping(value = "/songList/{id}")
    public Detail getSongListById(@PathVariable("id")Integer id) {
        SongList songList = songListManagerService.getSongListById(id);
        Map<Object, Object> map = new HashMap<>();
        map.put("songList", songList);
        detail.setDetail(map);
        return detail;
    }

    @ApiOperation("创建歌单(后台)")
    @PostMapping(value = "/musicManage/setSongSheet")
    public Detail insertSongList(@RequestParam("songListName") String name, @RequestParam("listDesc") String description,
                                 @RequestParam("coverPic") MultipartFile file) {
        String relativePath = null;
        //存储信息
        songList.setName(name);
        songList.setDescription(description);
        songList.setCreatorId(113568254);
        try {
            relativePath = FileOperator.fileUploud(file);
            songList.setCoverImgUrl("http://localhost:8080" + relativePath);
            songListManagerService.insertSongList(songList);
        } catch (IOException e) {
            detail.setCode(500);
            detail.setMessage("文件上传失败");
        } catch (Exception e){
            detail.setCode(500);
            detail.setMessage("服务器错误");
        }finally {
            return detail;
        }
    }

    /**
     *
     * @param name 用于修改歌单的 <code>name</code>属性
     * @param description 用于修改歌单的<code>description</code> 属性
     * @param id 用于匹配id 找到对应id
     * @return Detail  <code>code</code> 状态码：200 成功， 400 客户端错误， 500 服务器端错误
     */
    @ApiOperation("根据歌单ID修改歌单信息（后台）")
    @PostMapping(value = "/musicManage/putSongSheetbyId")
    public Detail updateSongList(@RequestParam("songListName") String name, @RequestParam("listDesc") String description,
                                 @RequestParam("id") Integer id) {
        songList.setName(name);
        songList.setDescription(description);
        songList.setId(id);
        try{
            songListManagerService.updateSongList(songList);
        }catch (Exception e){
            detail.setCode(500);
            detail.setMessage("服务器异常");
        }finally {
            return detail;
        }
    }

    @ApiOperation("根据歌单ID删除歌单（后台）")
    @PostMapping(value = "/musicManage/deleteSongSheetById")
    public Detail deleteSongListById(@RequestParam("id") Integer id) {
        try {
            songListManagerService.deleteSongListById(id);
        }catch (Exception e){
            detail.setCode(500);
            detail.setMessage("服务器错误");
        }finally {
            return detail;
        }
    }

    @ApiOperation("分页显示歌单（后台）")
    @GetMapping(value = "/musicManage/getSongSheetsByPages")
    public Detail getSongListByPages(@RequestParam("pages") int pages) {
        List<SongList> songLists = songListManagerService.getSongListByPages(pages);

        Map<Object, Object> map = new HashMap<>();
        map.put("songLists", songLists);
        detail.setDetail(map);
        return detail;
    }
}