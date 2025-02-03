package com.jphilips.taskmanagementv2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.taskmanagementv2.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
//	@Query("Select t from Task t where t.assignedTo.username =:username ORDER BY t.priority, t.id")
//	List<Task> findByAssignedTo(@Param("username") String username);
//	
//	@Query("Select t from Task t where assignedBy.username =:username ORDER BY t.priority, t.id")
//	List<Task> findByAssignedBy(@Param("username") String username);
	
	List<Task> findByAssignedTo_UsernameOrderByPriorityAscIdAsc(String username);
	
	List<Task> findByAssignedTo_UsernameAndStatusInOrderByPriorityAscIdAsc(String username, List<String> statuses);
	
	List<Task> findByAssignedBy_UsernameOrderByPriorityAscIdAsc(String username);
	
	List<Task> findByAssignedBy_UsernameAndStatusInOrderByPriorityAscIdAsc(String username, List<String> statuses);

	
}
