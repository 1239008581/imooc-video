package com.imooc.service;

import com.imooc.pojo.Bgm;

import java.util.List;

public interface BgmService {

    List<Bgm> getBgmList();

    Bgm queryBgmById(String bgmId);

}
