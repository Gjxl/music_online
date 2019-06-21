package com.dgut.music_online.service.Impl;

import com.dgut.music_online.dao.SongListDao;
import com.dgut.music_online.domain.SongList;
import com.dgut.music_online.service.SongListManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListManagerServiceImpl implements SongListManagerService {

    @Autowired
    SongListDao songListDao;

    @Override
    public void insertSongList(SongList songList) {
        songListDao.insertSongList(songList, songList.getUser().getId());
    }

    @Override
    public void insertSongIntoSongList(Integer songListId, Integer songId) {
        songListDao.insertSongIntoSongList(songListId, songId);
    }

    @Override
    public List<SongList> getAllSongLists() {
        return songListDao.getAllSongLists();
    }

    @Override
    public SongList getSongListById(Integer id) {
        SongList songList = songListDao.getSongListById(id);
        return songList;
    }
}
