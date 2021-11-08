package com.example.crud_redis_lntv.repository;

import com.example.crud_redis_lntv.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
//    private HashOperations hashOperations;//crud hash
    private RedisTemplate redisTemplate;
    private ListOperations listOperations;

    public EmployeeRepository(RedisTemplate redisTemplate) {

//        this.hashOperations = redisTemplate.opsForHash();
//        this.listOperations = redisTemplate.opsForList();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){

//        hashOperations.put("EMPLOYEE", employee.getId(), employee);
        listOperations.rightPush("EMPLOYEE", employee);
    }
    public List<Employee> findAll(){

//        return hashOperations.values("EMPLOYEE");
        return listOperations.range("EMPLOYEE", 0, -1);
    }
    public Employee findById(Integer id){

//        return (Employee) hashOperations.get("EMPLOYEE", id);
        List<Employee> list = listOperations.range("EMPLOYEE", 0, listOperations.size("EMPLOYEE"));
        for (Employee employee : list) {
            if(employee.getId() == id)
                return employee;
        }
        return null;
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
        listOperations.remove("EMPLOYEE", 1, findById(id));
    }
}
