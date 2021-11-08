package com.example.crud_redis_lntv.repository;

import com.example.crud_redis_lntv.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class EmployeeRepository {
//    private HashOperations hashOperations;//crud hash
    private RedisTemplate redisTemplate;
    private ListOperations listOperations;
    private SetOperations setOperations;

    public EmployeeRepository(RedisTemplate redisTemplate) {

//        this.hashOperations = redisTemplate.opsForHash();
//        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){

//        hashOperations.put("EMPLOYEE", employee.getId(), employee);
//        listOperations.rightPush("EMPLOYEE", employee);
        setOperations.add("EMPLOYEE", employee);
    }
    public List<Employee> findAll(){

//        return hashOperations.values("EMPLOYEE");
//        return listOperations.range("EMPLOYEE", 0, -1);
        return Arrays.asList((Employee[]) setOperations.members("EMPLOYEE").toArray());
    }
    public Employee findById(Integer id){

//        return (Employee) hashOperations.get("EMPLOYEE", id);
//        List<Employee> list = listOperations.range("EMPLOYEE", 0, listOperations.size("EMPLOYEE"));
//        for (Employee employee : list) {
//            if(employee.getId() == id)
//                return employee;
//        }
//        return null;
        return (Employee) setOperations.intersect("EMPLOYEE", id);
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
//        hashOperations.delete("EMPLOYEE", id);
//        listOperations.remove("EMPLOYEE", 1, findById(id));
        setOperations.remove("EMPLOYEE_SET", findById(id));
    }
}
