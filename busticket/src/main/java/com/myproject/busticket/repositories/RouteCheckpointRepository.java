package com.myproject.busticket.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Route_Checkpoint;
import org.springframework.data.repository.query.Param;

public interface RouteCheckpointRepository extends JpaRepository<Route_Checkpoint, Integer> {
    @Query("select distinct rc.checkpoint.province from Route_Checkpoint rc")
    List<String> findAllCheckpointProvinces();

    @Query("select distinct rc.checkpoint.city from Route_Checkpoint rc")
    List<String> findAllCheckpointCities();

    List<Route_Checkpoint> findByRoute(Route route);

    @Query("""
                SELECT CASE
                    WHEN rc.checkpoint.city = '' THEN rc.checkpoint.province
                    ELSE COALESCE(rc.checkpoint.city, rc.checkpoint.province)
                END
                FROM Route_Checkpoint rc
                WHERE
                    rc.route.code = :route_code AND rc.type = 'departure'
            """)
    String findDepartureName(@Param("route_code") String routeCode);

    @Query("""
                SELECT CASE
                    WHEN rc.checkpoint.city = '' THEN rc.checkpoint.province
                    ELSE COALESCE(rc.checkpoint.city, rc.checkpoint.province)
                END
                FROM Route_Checkpoint rc
                WHERE
                    rc.route.code = :route_code AND rc.type = 'drop_off'
            """)
    String findDropOffName(@Param("route_code") String routeCode);
}