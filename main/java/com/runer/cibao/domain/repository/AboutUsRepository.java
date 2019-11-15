package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AboutUsRepository extends JpaRepository<AboutUs,Long>,JpaSpecificationExecutor<AboutUs> {

}
