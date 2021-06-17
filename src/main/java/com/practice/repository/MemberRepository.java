package com.practice.repository;

import com.practice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByIdAndUserId(Long id, String userId);
}
