package org.example.quan_ly_diem.repository;

import org.example.quan_ly_diem.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    List<SinhVien> findAllByOrderByMaSvAsc();

    List<SinhVien> findByLopHanhChinh_MaLopHcOrderByMaSvAsc(String maLopHc);

    @Query("select s from SinhVien s left join s.lopHanhChinh l where (:lop='' or l.maLopHc=:lop) and (:khoa='' or l.maLopHc like concat(:khoa,'%')) and (:kw='' or lower(s.maSv) like lower(concat('%',:kw,'%')) or lower(s.hoTen) like lower(concat('%',:kw,'%'))) order by s.maSv")
    List<SinhVien> filter(@Param("khoa") String khoa, @Param("lop") String lop, @Param("kw") String kw);
}