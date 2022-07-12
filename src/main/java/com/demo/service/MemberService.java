package com.demo.service;

import com.demo.domain.Member;
import com.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public Flux<Member> getMembers() {
        return repository.findAll();
    }

    public Mono<Member> getMemberByUsername(String username) {
        return repository.findByUsername(username);
    }


}
