package com.haojishu.service;

import com.haojishu.domain.Users;
import com.haojishu.domain.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
  @Autowired UsersRepository usersRepository;

  // 判断ID是否存在
  private boolean existsById(Long id) {
    return usersRepository.existsById(id);
  }

  // 根据Id查询单条数据
  public Users findById(Long Id) {
    Users users = usersRepository.findById(Id).orElse(null);

    return users;
  }

  // 查找全部数据无分页
  public List<Users> findAll(Integer pageNumber, Integer rowPerPage) {
    List<Users> list = new ArrayList<>();
    Pageable pageable = PageRequest.of(pageNumber, rowPerPage, Sort.by(Sort.Direction.DESC, "id"));
    usersRepository.findAll(pageable).forEach(list::add);

    return list;
  }

  // 添加信息
  public String save(Users users) {
    if (!StringUtils.isEmpty(users.getUsername())) {
      if (users.getId() != null && existsById(users.getId())) {
        return "already";
      }
      usersRepository.save(users);
    } else {
      return "is empty";
    }
    return "success";
  }

  // 更新信息
  public String update(Users users) {
    if (!StringUtils.isEmpty(users.getUsername())) {
      if (!existsById(users.getId())) {
        return "is empty";
      }
      usersRepository.save(users);
    } else {
      return "is empty";
    }
    return "success";
  }

  // 删除信息
  public String del(Long Id) {
    if (!existsById(Id)) {
      return "is empty";
    }
    usersRepository.deleteById(Id);
    return "success";
  }

  // 信息总量
  public Long count() {
    return usersRepository.count();
  }
}
