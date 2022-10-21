package com.penta.ps.license.license.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "user_info", schema = "user", catalog = "user") // 'user_info' 테이블과 매핑
public class UserInfo /*implements UserDetails*/ {
    @Id // pk
    @Column(name = "\"id\"", nullable = false, length = 64)
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 출력 상황 배제
    @Column(name = "\"password\"",nullable = false, length = 128)
    private String password;

    @Column(name = "\"desc\"", nullable = true, length = 255)
    private String desc;

}
