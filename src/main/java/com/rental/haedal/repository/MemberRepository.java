package com.rental.haedal.repository;

import com.rental.haedal.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserIdAndPassword(String userId, String password);
    Optional<Member> findByUserId(String userId);
}
