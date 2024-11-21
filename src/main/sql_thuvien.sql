USE Final_QuanLyThuVien;
GO

/*-------------    Nhan Vien       ---------------- */
DROP TABLE IF EXISTS nhanvien;

CREATE TABLE nhanvien (
  manhanvien VARCHAR(10) NOT NULL ,
  tennhanvien NVARCHAR(45) NOT NULL,
  chucvu NVARCHAR(45) NULL,
  gioitinh NVARCHAR(45) NULL,
  ngaysinh DATE NULL,
  dienthoai int NOT NULL,
  email VARCHAR(100) NULL,
  tendangnhap VARCHAR(45) NOT NULL,
  matkhau VARCHAR(45) NOT NULL DEFAULT '123456',
  tinhtrang NVARCHAR(45) NULL,
  PRIMARY KEY (manhanvien),
);

INSERT INTO nhanvien (manhanvien, tennhanvien, chucvu, gioitinh, ngaysinh, dienthoai, email, tendangnhap, matkhau, tinhtrang)
VALUES 
    ('NV01', N'admin', 'admin', 'Nam', N'1990-04-30', '099999999', 'admin@gmail.com', 'admin', '123', NULL),
    ('NV02', N'Nguyễn Phương Thảo', N'Nhân viên', N'Nữ', '1995-08-07', '0888888888', 'thaonp@gmail.com', 'phuongthao', '123456', NULL),
    ('NV03', N'Nguyễn Quang Vinh', N'Nhân viên', N'Nam', '1990-04-30', '099999977', 'quangvinh.gulang@gmail.com', 'quangvinh', '123456', NULL),
    ('NV04', N'Trần Quang Anh', N'Nhân viên', N'Nam', '1998-09-18', '098998777', 'anhtq@gmail.com', 'quanganh', '123456', N'đã xóa'),
    ('NV05', N'Trần Văn Quang', N'Nhân viên', N'Nam', '1994-09-01', '0912121212', 'quangtv@gmail.com', 'vanquang', '123456', N'đã xóa'),
    ('NV06', N'Lê Đăng Khoa', N'Nhân viên', N'Nam', '1992-08-07', '0912341234', 'khoald@gmail.com', 'dangkhoa', '123456', NULL),
    ('NV07', N'Nguyễn Phương Nhi', N'Nhân viên', N'Nữ', '1995-08-07', '0888888888', 'nhinp@gmail.com', 'phuongnhi', '123456', N'đã xóa'),
    ('NV08', N'Trần Hải Linh', N'Nhân viên', N'Nữ', '1995-10-11', '0967537828', 'linhth@gmail.com', 'hailinh', '123456', N'đã xóa');


SELECT * FROM nhanvien WHERE tinhtrang is NULL AND tendangnhap = 'admin' and matkhau = '123'

SELECT * 
FROM nhanvien
WHERE LOWER(CAST(manhanvien AS VARCHAR) + 
            CAST(tennhanvien AS VARCHAR) + 
            CAST(chucvu AS VARCHAR) + 
            CAST(gioitinh AS VARCHAR) + 
            CAST(ngaysinh AS VARCHAR) + 
            CAST(dienthoai AS VARCHAR) + 
            CAST(email AS VARCHAR) + 
            CAST(tendangnhap AS VARCHAR) + 
            CAST(matkhau AS VARCHAR) + 
            CAST(tinhtrang AS VARCHAR)) 
      LIKE '%quang%'

SELECT * 
FROM nhanvien 
WHERE LOWER(manhanvien) LIKE ? 
   OR LOWER(tennhanvien) LIKE ? 
   OR LOWER(chucvu) LIKE ? 
   OR LOWER(gioitinh) LIKE ? 
   OR LOWER(ngaysinh) LIKE ? 
   OR LOWER(dienthoai) LIKE ? 
   OR LOWER(email) LIKE ? 
   OR LOWER(tendangnhap) LIKE ? 
   OR LOWER(trangthai) LIKE ?


/*-------------    Sach       ---------------- */

DROP TABLE IF EXISTS sach;

CREATE TABLE sach (
  masach varchar(11) NOT NULL,
  tensach nvarchar(45) DEFAULT NULL,
  theloai nvarchar(45) DEFAULT NULL,
  tacgia nvarchar(45) DEFAULT NULL,
  nhaxuatban nvarchar(45) DEFAULT NULL,
  namxuatban int DEFAULT NULL,
  ngaynhap date DEFAULT NULL,
  ngonngu nvarchar(45) DEFAULT NULL,
  sotrang int DEFAULT NULL,
  soluong int DEFAULT NULL,
  giatien int DEFAULT 0,
  damuon int DEFAULT 0,
  tinhtrang nvarchar(45) DEFAULT NULL,
  PRIMARY KEY (masach),
)

INSERT INTO sach(masach, tensach, theloai, tacgia, nhaxuatban, namxuatban, ngaynhap, ngonngu, sotrang, soluong, giatien, damuon, tinhtrang)
VALUES  ('MS01', N'Toán cao cấp', N'Toán học', N'Trịnh Hoàng Hải', N'Kim Đồng',2016,'2016-08-10', N'Việt',140,3,130000,0,NULL),
		('MS02', N'Tin học đại cương', N'Tin học', N'Lê Thị Lan', N'Giáo dục và đào tạo',2013,'2017-03-03', N'Việt',210,6,210000,0, N'đã xóa'),
		('MS03', N'java', N'Tin học', N'Nguyễn Văn Bé', N'Bách Khoa',2008,'2017-03-18', N'Anh',150,6,200000,6,NULL),
		('MS04', N'Hệ quản trị cơ sở dữ liệu', N'Tin học', N'Nhóm tác giả', N'Đại học quốc gia HN',2016,'2017-09-09', N'Việt',210,9,210000,8,NULL),
		('MS05', N'Nhật Ký Trong Tù', N'Văn học', N'Hồ Chí Minh', N'Giáo Dục',1999,'2016-09-17', N'Việt',274,3,13000,3,NULL),
		('MS06', N'Thơ lục bát', N'Văn học', N'Hoàng Anh', N'Bách Khoa HN',2015,'2017-09-09', N'Việt',140,8,180000,5,NULL),
		('MS07', N'Toán rời rạc', N'Toán học', N'Lưu Gia Hải', N'Bách Khoa HN',2015,'2017-09-09', N'Anh',120,3,150000,1,NULL),
		('MS08', N'Thực hành Java', N'Tin học', N'Nguyễn Quang Vinh', N'Tại gia',2017,'2017-09-09', N'Việt',100,0,200000,0, N'đã xóa'),
		('MS09', N'Kỹ năng nuôi con', N'Tin học', N'Trần Mạnh Hải', N'Hồng Đức',2017,'2017-09-09', N'Việt',100,5,120000,0,NULL),
		('MS10', N'Linux','Khác', N'Trần Văn Hạnh', N'Đại học quốc gia',2017,'2017-09-21', N'Việt',110,3,100000,0,NULL),
		('MS11', N'Ngôn ngữ lập trình C', N'Tin học', N'Nguyễn Văn Tài', N'Bộ Giáo dục và đào tạo',2017,'2017-09-23', N'Việt',200,3,200000,0,NULL),
		('MS12', N'excel import 3', N'Khác', N'Nguyễn Bá Học', N'Kim Đồng',2016,'2017-09-15', N'Việt',100,0,100000,0, N'đã xóa');


SELECT * FROM Sach
WHERE tinhtrang IS NULL 
  AND soluong > 0 
  AND LOWER(masach + tensach + theloai + tacgia) 
      LIKE LOWER('%' + masach + '%');

		SELECT * from Sach 
        where tinhtrang IS NULL AND soluong > 0 AND lower(masach + tensach + theloai + tacgia)
        like lower('%' + masach + '%');

SELECT * 
FROM Sach 
WHERE LOWER(masach + tensach + theloai + tacgia + nhaxuatban + namxuatban + ngonngu + giatien + damuon + tinhtrang) LIKE '%' + LOWER('kim') + '%';

SELECT * FROM Sach WHERE LOWER(masach) LIKE ? OR LOWER(tensach) LIKE ? OR LOWER(theloai) LIKE ?

SELECT * 
FROM Sach 
WHERE LOWER(CAST(masach AS VARCHAR) + CAST(tensach AS VARCHAR) + CAST(theloai AS VARCHAR) + 
            CAST(tacgia AS VARCHAR) + CAST(nhaxuatban AS VARCHAR) + 
            CAST(namxuatban AS VARCHAR) + CAST(ngonngu AS VARCHAR) + 
            CAST(giatien AS VARCHAR) + CAST(damuon AS VARCHAR) + 
            CAST(tinhtrang AS VARCHAR)) 
            LIKE '%' + LOWER('gia') + '%';

SELECT masach FROM muontra m
WHERE ngaytra IS NULL AND madocgia = maDocGia 


/*-------------    Doc Gia       ---------------- */

DROP TABLE IF EXISTS docgia;

CREATE TABLE docgia (
  madocgia varchar(20) NOT NULL,
  tendocgia nvarchar(100) NOT NULL,
  ngaysinh date DEFAULT NULL,
  gioitinh nvarchar(8) DEFAULT NULL,
  diachi nvarchar(300) DEFAULT NULL,
  email varchar(45) DEFAULT NULL,
  dienthoai int DEFAULT NULL,
  trangthai nvarchar(45) DEFAULT NULL,
  ngaylapthe date DEFAULT NULL,
  PRIMARY KEY (madocgia),
)

INSERT INTO docgia (madocgia, tendocgia, ngaysinh, gioitinh, diachi, email, dienthoai, trangthai, ngaylapthe)
VALUES  ('MDG01', N'Trần Văn Anh','1980-01-17', N'Nam', N'Thanh Trì','atv@gmail.com','0123456789',NULL,'2024-09-21'),
		('MDG02', N'Nguyễn Văn Bình','1999-05-29', N'Nam', N'Long Biên','bnv@gmail.com','012121212',NULL,'2024-09-21'),
		('MDG03', N'Hà Thị Thảo','1988-09-11', N'Nữ', N'Cầu Giấy - Hà Nội','thaoht@gmail.com','0909989789',NULL,'2024-09-21'),
		('MDG04', N'Trần Thị Lan','1991-09-13', N'Nữ', N'Hoàng Mai - Hà Nội','lantt@gmail.com','0123123112', N'đã xóa','2024-09-21'),
		('MDG05', N'Văn Ngọc Linh','1993-09-01','Nam', N'TP Hà Nam','linhvn@gmail.com','01235774593',NULL,'2024-09-21'),
		('MDG06', N'Lê Huy Hải','1994-09-02','Nam','Phúc Yên, Vĩnh Phúc','hailh@gmail.com','0123123123',NULL,'2024-09-21'),
		('MDG07', N'Trần Lê lan','1990-09-17', N'Nữ', N'Bắc Kạn','lantl@gmail.com','067777776',NULL,'2024-10-02'),
		('MDG08', N'Nguyễn Phương Anh','1995-05-25', N'Nữ', N'Hà Nam','anhnp@gmail.com','0123123456',NULL,'2024-09-21'),
		('MDG09', N'Nguyễn Linh Loan','1994-07-07', N'Nữ', N'Ninh Bình','loannl@gmail.com','096785564',NULL,'2024-09-21'),
		('MDG10', N'tesst 3','1988-09-17', N'Nam', N'Cầu Giấy','teest3@gmail.com','123123157', N'đã xóa','2024-09-21'),
		('MDG11', N'sdfsgf','2017-09-02', N'Nam', N'fgh n ','bnmngh','0789789',NULL,'2024-09-21'),
		('MDG12', N'sadmkj jgh','2005-09-02', N'Nam', N'asfdas','dfhgfhfgh','0567567456',NULL,'2024-09-21'),
		('MDG13', N'Lê Như Ngọc','1994-09-01', N'Nữ', N'Thanh Xuân - Hà Nội','ngocln@gmail.com','0912121212',NULL,'2024-09-21'),
		('MDG14', N'Trần Văn Tuấn','1992-09-24', N'Nam', N'TP Đà Nẵng','tuantv@gmail.com','099999767',NULL,'2024-09-21'),
		('MDG15', N'Nguyễn Tuệ Lan','1997-12-30', N'Nữ', N'Long Biên - Hà Nội','lannt@gmail.com','0988516518',NULL,'2024-09-21'),
		('MDG16', N'tesst 2','1988-09-16', N'Nữ', N'Thanh Xuân','ttest2@gmail.com','098789898', N'Hết hạn','2024-09-21'),
		('MDG17', N'Lê Việt Hải','1988-09-15', N'Nam', N'Long Bien','hailv@gmail.com','098767898',NULL,'2024-09-21'),
		('MDG18', N'tesst 2','1988-09-16', N'Nữ', N'Thanh Xuân','ttest2@gmail.com','088887878', N'đã xóa','2024-09-21'),
		('MDG19', N'tesst 1','1988-09-15', N'Nam', N'Long Bien','test1@gmail.com','098767898',NULL,'2024-09-21'),
		('MDG20', N'tesst 2','1988-09-16', N'Nữ', N'Thanh Xuân','ttest2@gmail.com','88887878', N'đã xóa','2024-09-21'),
		('MDG21', N'tesst 3','1988-09-17', N'Nam', N'Cầu Giấy','teest3@gmail.com','0123123157',NULL,'2024-09-21'),
		('MDG22', N'tesst 1','1988-09-15', N'Nam', N'Long Bien','test1@gmail.com','098767898',NULL,'2024-09-21'),
		('MDG23', N'tesst 2','1988-09-16', N'Nữ', N'Thanh Xuân','ttest2@gmail.com','088887878',NULL,'2024-09-21'),
		('MDG24', N'tesst 3','1988-09-17', N'Nam', N'Cầu Giấy','teest3@gmail.com','0123123157',NULL,'2024-09-21'),
		('MDG25', N'Nguyễn Mạnh Linh','1997-09-02', N'Nam', N'Hoàng Mai - Hà Nội','linhnm@gmail.com','012357',NULL,'2024-09-21'),
		('MDG26', N'Lê Thị Mai','1988-09-21', N'Nữ', N'Hoàng Mai - Hà Nội','mailt@gmail.com','0123123133',NULL,'2024-09-21'),
		('MDG27', N'Lê Như Ngọc','1994-09-01', N'Nữ', N'Thanh Xuân - Hà Nội','ngocln@gmail.com','0912121212',NULL,'2024-09-21'),
		('MDG28', N'tesst hg','1988-09-15', N'Nam', N'Long Bien','test1@gmail.com','98767898', N'đã xóa','2024-09-21'),
		('MDG29', N'Trần Văn Nguyên','1980-01-17', N'Nam', N'Thanh Trì','atv@gmail.com','123456789',NULL,'2024-09-21'),
		('MDG30', N'Trần Văn Nguyên','1980-01-17', N'Nam', N'Thanh Trì','atv@gmail.com','0123456789',NULL,'2024-09-21'),
		('MDG31', N'Trần Văn Nguyên','1980-01-17', N'Nam', N'Thanh Trì','atv@gmail.com','0123456789',NULL,'2024-09-21'),
		('MDG32', N'Nguyễn Thị Lan','1994-08-15', N'Nữ', N'Long Biên','lannt@gmail.com','0967868789',NULL,'2024-09-21'),
		('MDG33', N'Việt Nam','1991-05-19', N'Nữ', N'Long Biên','lannt@gmail.com','0967868789',NULL,'2024-09-21'),
		('MDG34', N'Việt Nam','1991-05-19', N'Nữ', N'Long Biên','lannt@gmail.com','0967868789',NULL,'2024-09-21'),
		('MDG35', N'tesst hg1','1988-09-15', N'Nam', N'Long Bien','test1@gmail.com','098767898',NULL,'2024-09-21'),
		('MDG36', N'Test Hết Hạn','1980-01-17', N'Nam', N'Thanh Trì','atv@gmail.com','0123456789', N'Hết hạn','2024-08-01'),
		('MDG37', N'hết hạn test','1988-09-15', N'Nam', N'Long Bien','test1@gmail.com','098767898', N'Hết hạn','2024-10-05'),
		('MDG38', N'Trần Văn Tình','1988-09-02', N'Nam', N'fgh n ','bnmngh','0789789',NULL,'2024-09-21'),
		('MDG39', N'hà thi trang','1988-08-15', N'Nữ', N'hà nội','trang','09000378',NULL,'2024-10-12');


/*-------------    Muon Tra       ---------------- */

DROP TABLE IF EXISTS muontra;

CREATE TABLE muontra (
    mamuontra varchar(50) NOT NULL,
    madocgia varchar(20) NOT NULL foreign key references docgia(madocgia),
    masach varchar(11) NOT NULL foreign key references sach(masach),
    manhanvien varchar(10) NOT NULL foreign key references nhanvien(manhanvien),
    ngaymuon DATE NOT NULL,
    ngaytra DATE NULL,
    maphieu varchar(50) NOT NULL,
    PRIMARY KEY (mamuontra)
);

INSERT INTO muontra(mamuontra, madocgia, masach, manhanvien, ngaymuon, ngaytra, maphieu)
VALUES  ('MMT01', 'MDG02', 'MS03', 'NV01', '2024-09-09', '2017-09-18', 'MP01'),
		('MMT02', 'MDG05', 'MS03', 'NV02', '2024-07-15', '2017-09-17', 'MP02'),
		('MMT03', 'MDG09', 'MS02', 'NV01', '2024-08-20', NULL, 'MP03'),
		('MMT04', 'MDG02', 'MS05', 'NV02', '2024-09-09', '2017-09-17', 'MP04'),
		('MMT05', 'MDG03', 'MS03', 'NV02', '2024-08-10', NULL, 'MP05'),
		('MMT06', 'MDG03', 'MS05', 'NV03', '2024-07-19', '2017-09-30', 'MP06'),
		('MMT07', 'MDG02', 'MS02', 'NV01', '2024-09-17', NULL, 'MP07'),
		('MMT08', 'MDG02', 'MS03', 'NV01', '2024-09-17', '2017-09-17', 'MP08'),
		('MMT10', 'MDG02', 'MS06', 'NV01', '2024-09-17', NULL, 'MP09'),
		('MMT11', 'MDG02', 'MS01', 'NV01', '2024-09-17', '2017-09-19', 'MP10'),
		('MMT12', 'MDG04', 'MS03', 'NV01', '2024-09-17', '2017-09-19', 'MP11'),
		('MMT13', 'MDG04', 'MS05', 'NV01', '2024-09-17', NULL, 'MP12'),
		('MMT14', 'MDG04', 'MS06', 'NV01', '2024-09-17', '2017-09-30', 'MP13'),
		('MMT15', 'MDG01', 'MS02', 'NV01', '2024-09-17', '2017-09-30', 'MP15'),
		('MMT16', 'MDG10', 'MS02', 'NV02', '2024-07-01', '2017-09-01', 'MP16'),
		('MMT17', 'MDG03', 'MS06', 'NV03', '2024-08-01', NULL, 'MP17'),
		('MMT18', 'MDG03', 'MS01', 'NV01', '2024-09-17', NULL, 'MP18'),
		('MMT19', 'MDG03', 'MS05', 'NV01', '2024-09-17', '2017-09-18', 'MP19'),
		('MMT20', 'MDG03', 'MS02', 'NV01', '2024-09-17', NULL, 'MP20'),
		('MMT21', 'MDG06', 'MS01', 'NV01', '2024-09-17', '2017-10-03', 'MP21'),
		('MMT22', 'MDG06', 'MS02', 'NV01', '2024-09-17', NULL, 'MP22'),
		('MMT23', 'MDG05', 'MS01', 'NV01', '2024-09-17', '2017-09-17', 'MP23'),
		('MMT24', 'MDG05', 'MS02', 'NV01', '2024-09-17', '2017-09-30', 'MP24'),
		('MMT25', 'MDG02', 'MS03', 'NV01', '2024-09-19', '2017-09-2', 'MP25'),
		('MMT26', 'MDG02', 'MS01', 'NV01', '2024-09-22', NULL, 'MP26'),
		('MMT27', 'MDG03', 'MS01', 'NV01', '2024-09-22', '2017-09-22', 'MP27'),
		('MMT28', 'MDG05', 'MS01', 'NV01', '2024-09-22', '2017-10-11', 'MP28'),
		('MMT29', 'MDG06', 'MS01', 'NV01', '2024-09-22', NULL, 'MP29'),
		('MMT30', 'MDG02', 'MS03', 'NV01', '2024-09-24', '2017-10-03', 'MP30'),
		('MMT31', 'MDG02', 'MS02', 'NV01', '2024-09-25', NULL, 'MP31'),
		('MMT32', 'MDG03', 'MS01', 'NV01', '2024-09-25', '2017-09-27', 'MP32'),
		('MMT33', 'MDG03', 'MS06', 'NV01', '2024-09-25', '2017-09-30', 'MP33');

SELECT masach FROM muontra WHERE
ngaytra IS NOT NULL AND madocgia = 'MDG05'

SELECT COUNT(madocgia) as dangmuon FROM muontra WHERE masach = 'MS03' AND ngaytra IS NULL

SELECT maphieu, mamuontra, m.madocgia, tendocgia, m.masach, tensach, ngaymuon 
FROM muontra m, sach s, docgia d 
WHERE m.ngaytra IS NULL AND LOWER(CAST(m.madocgia AS VARCHAR) + 
								  CAST(d.tendocgia AS VARCHAR) + 
								  CAST(m.mamuontra AS VARCHAR) + 
								  CAST(s.tensach AS VARCHAR))
								  LIKE '%quang%'
						AND s.masach = m.masach AND d.madocgia = m.madocgia 
						ORDER BY mamuontra DESC

SELECT mamuontra,m.madocgia,tendocgia,m.masach,tensach,ngaymuon
FROM muontra m, sach s, docgia d 
WHERE m.ngaytra IS NULL AND m.madocgia = 'MDG02'
                        AND s.masach = m.masach AND d.madocgia = m.madocgia 
ORDER BY mamuontra DESC

SELECT m.maphieu, m.mamuontra, m.madocgia, tendocgia, m.masach, s.tensach, m.ngaymuon
FROM muontra m, sach s, docgia d 
WHERE m.ngaytra IS NULL AND LOWER(CAST(m.mamuontra AS VARCHAR) +
                                  CAST(m.maphieu AS VARCHAR) + 
								  CAST(m.madocgia AS VARCHAR) +
                                  CAST(d.tendocgia AS VARCHAR) +
                                  CAST(m.masach AS VARCHAR) +
                                  CAST(s.tensach AS VARCHAR) +
                                  CAST(m.ngaymuon AS VARCHAR)) 
                             LIKE '%MP03%'
                         AND s.masach = m.masach AND d.madocgia = m.madocgia
                    ORDER BY mamuontra ASC;  

SELECT d.*, s.*, m.ngaymuon
FROM muontra m, sach s, docgia d 
WHERE m.mamuontra = 'MMT03' AND s.masach = m.masach AND d.madocgia = m.madocgia

SELECT COUNT(masach) 
FROM muontra 
WHERE madocgia = 'MDG02' AND ngaytra IS NULL

SELECT s.masach, s.tensach, s.theloai, s.tacgia, s.nhaxuatban
FROM sach s, muontra m
WHERE m.maphieu = 'MP03' AND s.masach = m.masach;



DROP TABLE IF EXISTS vipham;

CREATE TABLE vipham (
  mamuontra varchar(50) NOT NULL foreign key references muontra(mamuontra),
  quahan int DEFAULT NULL,
  lydo nvarchar(45) DEFAULT NULL,
  xuly nvarchar(45) DEFAULT NULL,
)

INSERT INTO vipham VALUES 
		('MMT04',14, N'Quá hạn',N'Đã xử lý'),
		('MMT05',48, N'Quá hạn',NULL);

SELECT v.mamuontra, m.madocgia, d.tendocgia, s.tensach, v.quahan, v.lydo, m.ngaymuon, m.ngaytra 
FROM muontra m, sach s, vipham v, docgia d 
WHERE m.mamuontra = v.mamuontra AND d.madocgia= m.madocgia AND s.masach = m.masach
AND v.xuly IS NULL AND LOWER(CAST(m.madocgia AS VARCHAR) +
                             CAST(d.tendocgia AS VARCHAR) + 
							 CAST(s.tensach AS VARCHAR) +
							 CAST(v.lydo AS VARCHAR) +
							 CAST(m.mamuontra AS VARCHAR) +
							 CAST(m.ngaymuon AS VARCHAR) +
                             CAST(m.ngaytra  AS VARCHAR) +
                             CAST(s.masach AS VARCHAR)) 
                  like '%MVP02%'
ORDER BY m.madocgia ASC;

SELECT v.mamuontra, m.madocgia, d.tendocgia, s.tensach, v.quahan, v.lydo, m.ngaymuon, m.ngaytra 
FROM muontra m, sach s, vipham v, docgia d 
WHERE m.mamuontra = v.mamuontra AND d.madocgia= m.madocgia AND s.masach = m.masach 
AND v.xuly IS NULL
AND LOWER (CAST(d.madocgia  AS VARCHAR) +
          CAST(d.tendocgia AS VARCHAR) + 
          CAST(v.lydo AS VARCHAR) + 
          CAST(m.mamuontra AS VARCHAR) + 
          CAST(m.ngaymuon AS VARCHAR) + 
          CAST(s.masach AS VARCHAR) +
          CAST(s.tensach AS VARCHAR) +
          CAST(m.ngaytra AS VARCHAR))
          LIKE ? 
    ORDER BY madocgia ASC; 


SELECT m.madocgia,tendocgia, COUNT(m.madocgia) as soLuotMuon
FROM muontra m, docgia d where d.madocgia = m.madocgia 
and (ngaymuon between '2024-10-10' and '2024-11-20')
GROUP BY m.madocgia, tendocgia
ORDER BY soLuotMuon DESC

SELECT v.mamuontra, m.madocgia, d.tendocgia, s.tensach, quahan, lydo, m.ngaymuon, m.ngaytra, v.xuly 
FROM muontra m, sach s, vipham v, docgia d
WHERE m.mamuontra = v.mamuontra AND d.madocgia= m.madocgia AND s.masach= m.masach
                                AND (m.ngaytra BETWEEN '2024-10-01' AND '2024-11-20') 
								ORDER BY d.madocgia DESC

SELECT masach, tensach, soluong
FROM sach 
WHERE soluong = damuon