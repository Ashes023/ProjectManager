package com.ashahar.projectmanagementsystem.repo;

import com.ashahar.projectmanagementsystem.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

    Subscription findByUserId(Long userId);

}
