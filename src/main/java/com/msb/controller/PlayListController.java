package com.msb.controller;

import com.msb.bean.PasswordDTO;
import com.msb.bean.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-05 15:54
 */
@Controller
public class PlayListController {

    @RequestMapping("/play-list-view-thy")
    public String getPlayList(final Model model) {
        List<Song> songs = new ArrayList<>();
        Song song = null;
        for (int i = 0; i < 1000; i++) {
            song = new Song(i, "张三"+i, "1001"+i, "专辑"+i);
            songs.add(song);
        }
        // 转化成响应式流
        Flux<Song> playListStream = Flux.fromIterable(songs)
                .delayElements(Duration.ofMillis(500)); // 500ms产生一个数据
        model.addAttribute("playList", new ReactiveDataDriverContextVariable(playListStream, 1, 1));
        // 渲染数据
        return "play-list-view";
    }

}
