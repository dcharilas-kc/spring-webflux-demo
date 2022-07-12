package com.demo.controller;

import com.demo.domain.Member;
import com.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{username}")
    private Mono<Member> getEmployeeById(@PathVariable String username) {
        return memberService.getMemberByUsername(username);
    }

    @GetMapping
    private Flux<Member> getAllEmployees() {
        return memberService.getMembers();
    }


}