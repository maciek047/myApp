package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        URI url = generateBoardsUrl();
        List<TrelloBoardDto> result = new ArrayList<>();
        Optional<TrelloBoardDto[]> boardsResponse = Optional.ofNullable(restTemplate.getForObject(url, TrelloBoardDto[].class));
        if(boardsResponse.isPresent()){
            result = boardsResponse.stream()
                    .flatMap(Stream::of)
                    .collect(Collectors.toList());
//            result = boardsResponse.map(Arrays::asList)
//                    .stream()
//                    .flatMap(n -> n.stream())
//                    .collect(Collectors.toList());

        }
        return result;
    }

    private URI generateBoardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/"+trelloUsername+"/boards")
                .queryParam("key",trelloAppKey)
                .queryParam("token",trelloToken)
                .queryParam("fields", "name,id").build().encode().toUri();
    }
}
