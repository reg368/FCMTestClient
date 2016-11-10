xx

CREATE TABLE Ap(
	[apId] [int] IDENTITY(1,1) NOT NULL,
	[apEName] [varchar](50) NULL,
	[apCName] [nvarchar](30) NOT NULL,
	[apCat] [nvarchar](10) NULL,
	[apUrl] [nvarchar](80) NULL,
	[apSort] [int] NOT NULL,
	primary key(apId)
);

CREATE TABLE ApCat (
	[apCatId] [varchar](10) NOT NULL,
	[apCatCName] [nvarchar](50) NOT NULL,
	[apCatEName] [varchar](30) NULL,
	[apCatSort] [int] NOT NULL,
	[apCatIconUrl] [nvarchar](50) NULL,
	primary key(apCatId)
);



CREATE TABLE InfoUser (
	[userId] [nvarchar](50) NOT NULL,
	[password] [varchar](100) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[nickName] [nvarchar](100) NULL,
	[sex] [char](1) NULL,
	[birthday] [datetime] NULL,
	[tel] [varchar](100) NULL,
	[cellPhone] [varchar](100) NULL,
	[email] [nvarchar](100) NOT NULL,
	[deptId] [int] NOT NULL,
	[jobTitles] [nvarchar](100) NULL,
	[createUser] [nvarchar](50) NULL,
	[createTime] [datetime] NULL,
	[ugrpId] [nvarchar](100) NULL,
	[status] [char](1) NULL,
	[visitCount] [int] NULL,
	[postLimit] [varchar](50) NULL,
	[reviewLimit] [varchar](50) NULL,
	[shelvesLimit] [varchar](50) NULL,
	[updateUser] [nvarchar](50) NULL,
	[updateTime] [datetime] NULL,
	primary key (userId)
 );
 
insert into InfoUser (userId,password,name,nickName,sex,birthday,tel,cellPhone,email,deptId,jobTitles,ugrpId,status,visitCount,postLimit,reviewLimit,updateUser,updateTime) values
('cc','e0323a9039add2978bf5b49550572c7c','VemZuFXkupUK6LKQ','管理者帳號','M','2013-06-20','','VTBVbQppWmFVbAMyUzBYYAFsB2w=','VWNVNw==',1,'','22','Y',3088,'45,56,57,58,59,60,61,62,63','45,56,57,58,59,60,61,62,63','cc','2013-06-25 10:29:49') ;


CREATE TABLE [dbo].[InfoUserLogin](
	[loginSeq] [bigint] IDENTITY(1,1) NOT NULL,
	[loginUserId] [nvarchar](50) NOT NULL,
	[lastVisit] [datetime] NOT NULL,
	[lastIp] [nvarchar](30) NOT NULL,
	primary key(loginSeq)
);





CREATE TABLE Dept(
	[deptSeq] [int] NOT NULL,
	[deptId] [nvarchar](20) NULL,
	[deptName] [nvarchar](20) NOT NULL,
	[deptEName] [nvarchar](30) NULL,
	[deptInUse] [char](1) NOT NULL,
	[deptHierarchyCode] [varchar](30) NOT NULL,
	[deptSort] [int] NOT NULL,
	primary key(deptSeq)
);

--insert into Dept (deptSeq,deptId,deptName,deptEName,deptInUse,deptHierarchyCode,deptSort) values (1,'company','今周刊','','1','1',0);


CREATE TABLE [dbo].[Ugrp](
	[ugrpId] [int] IDENTITY(1,1) NOT NULL,
	[ugrpName] [nvarchar](10) NOT NULL,
	[remark] [nvarchar](50) NULL,
	primary key(ugrpId)
);



CREATE TABLE [dbo].[UgrpAp](
	[ugrpId] [int] NOT NULL,
	[apId] [int] NOT NULL,
	[apFunc] [int] NOT NULL,
	primary key(ugrpId,apId)
);



CREATE TABLE [dbo].[CodeMain](
	[codeSeq] [int] IDENTITY(1,1) NOT NULL,
	[codeId] [nvarchar](20) NOT NULL,
	[codeValue] [nvarchar](200) NOT NULL,
	[codeShow] [nvarchar](200) NOT NULL,
	[codeSort] [int] NOT NULL,
	primary key (codeSeq)
);

CREATE TABLE [dbo].[CodeMetaDef](
	[codeMetaSeq] [int] IDENTITY(1,1) NOT NULL,
	[codeMetaId] [nvarchar](20) NOT NULL,
	[codeMetaName] [nvarchar](100) NOT NULL,
	[codeMetaTblName] [nvarchar](50) NOT NULL,
	[codeMetaSrcFld] [nvarchar](20) NULL ,
	[codeMetaSrcItem] [nvarchar](100) NULL,
	[codeMetaValueFld] [nvarchar](20) NOT NULL,
	[codeMetaValueFldName] [nvarchar](20) NOT NULL,
	[codeMetaShowFld] [nvarchar](20) NOT NULL,
	[codeMetaShowFldName] [nvarchar](20) NOT NULL,
	[codeMetaSortFld] [nvarchar](20) NULL,
	[codeMetaSortFldName] [nvarchar](20) NOT NULL,
	[codeMetaType] [nvarchar](20) NULL,
	[showOrNot] [char](1) NOT NULL,
	[codeMetaSort] [int] NOT NULL,
	primary key(codeMetaSeq)
);


ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('CodeMain') FOR [codeMetaTblName] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('codeId') FOR [codeMetaSrcFld] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('codeValue') FOR [codeMetaValueFld] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('代碼值') FOR [codeMetaValueFldName] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('codeShow') FOR [codeMetaShowFld] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('顯示值') FOR [codeMetaShowFldName] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('codeSort') FOR [codeMetaSortFld] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('排序值') FOR [codeMetaSortFldName] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ('無分類') FOR [codeMetaType] ;
ALTER TABLE [dbo].[CodeMetaDef] ADD  DEFAULT ((0)) FOR [codeMetaSort] ;


CREATE TABLE [dbo].[CodeRef](
	[seq] [int] IDENTITY(1,1) NOT NULL,
	[refId] [nvarchar](20) NOT NULL,
	[refValue] [nvarchar](20) NOT NULL,
	[subCodeValue] [nvarchar](20) NOT NULL,
	[subCodeShow] [nvarchar](50) NOT NULL,
	[subCodeSort] [int] NOT NULL,
	primary key(seq)
);


CREATE TABLE [dbo].[CodeShowEtc](
	[codeId] [int] NOT NULL,
	[showValue] [nvarchar](200) NOT NULL,
	primary key(codeId)
 );



CREATE TABLE [dbo].[Seq](
	[name] [nvarchar](15) NOT NULL,
	[val] [bigint] NOT NULL,
	primary key(name)
);

CREATE TABLE DataSet(
	[dsId] [bigint] NOT NULL,
	[dsType] [nvarchar](20) NOT NULL,
	[dsTitle] [nvarchar](100) NOT NULL,
	[dsSubTitle] [nvarchar](100) NULL,
	[dsContent] [nvarchar](max) NULL,
	[keyword1] [nvarchar](100) NULL,
	[keyword2] [nvarchar](100) NULL,
	[dsStatus] [char](1) NOT NULL,
	[dsIsPublic] [char](1) NOT NULL,
	[dsPostDate] [datetime] NOT NULL,
	[dsPermission] [char](1) NOT NULL,
	[createUser] [nvarchar](50) NOT NULL,
	[createDate] [datetime] NULL,
	[editUser] [nvarchar](50) NULL,
	[editDate] [datetime] NULL,
	[diImgSeq] [nvarchar](50) NULL,
	[diImgMainSeq] [nvarchar](10) NULL,
	[dfFileSeq] [nvarchar](50) NULL,
	[dsUrl] [nvarchar](256) NULL,
	[dsCapture] [nvarchar](500) NULL,
	[dsWeek] [int] NULL,
	[dsInWeek] [varchar](10) NULL,
	[dsOutWeek] [varchar](10) NULL,
	[phase] [nvarchar](100) NULL,
	[author] [nvarchar](30) NULL,
	[type] [nvarchar](10) NULL,
	primary key(dsId)
)


-- 檢驗組織
create table psTestOrg (
	toId int IDENTITY(1,1) primary key , 
	toName nvarchar(64),
	toTel  nvarchar(15),
	toAddr nvarchar(128),
	toStatus char(1) default '1' ,
	toSort int , 
	toORGId int 
);

-- 檢驗作物
CREATE TABLE [dbo].[psTestCrop](
	tcId int IDENTITY(1,1) primary key , 
	[cat1] [char](1) NOT NULL,
	[cat2] [char](2) NOT NULL,
	[cat3] [char](2) NOT NULL,
	tcStatus char(1) default '1' ,
	tcSort int,
	tcTestOrgList varchar(128) ,
	tcOrgId int
)
   
-- 檢驗項目
CREATE TABLE [dbo].[psTestItem](
	tcId int IDENTITY(1,1) primary key , 
	tiCropId varchar(5) ,   -- 作物代碼
	[tiTestCHTName] [nvarchar](50) NULL,
	[tiTestENGName] [nvarchar](50) NULL,
	[tiMethodLimit] [numeric](20, 4) NULL,
	tiLawCond char(1) default '1',  -- <= 1 , = 2
	[tiLawItem] [numeric](20, 0) NULL,
	tiNotAllow char(1) null ,  -- 1 驗出
	tiStatus char(1) default '1' , 
	[tiORGId] [int] NULL,
	tiSort int ,
	[licNo] [nvarchar](50) NULL,
);





	



   
create view psTestItemView as
select *, 
(select codeShow from codemain where codeValue = '1' and codeId='toStatus' ) tiStatusText 
from psTestItem

-- 檢驗報告
CREATE TABLE [dbo].[psTestReport](
	rid int IDENTITY(1,1) primary key , 
	trTestDate datetime ,   -- 送檢日期
	trSCropDate datetime ,  -- 作物種植啟日
	trECropDate  datetime,  -- 作物種植迄日
	tcId int , -- 檢驗作物名稱
	cat1 varchar(10) , -- 檢驗加工品項
	cat2 varchar(10) , -- 檢驗加工品項
	cat3 varchar(10)  ,-- 檢驗加工品項
	cat4 varchar(10) ,
	cat5 varchar(10) ,
	trTestOrgId int ,  -- 檢驗機構
	trSeq nvarchar(64) , -- 檢測品編號
	trFarmerId varchar(12) , -- 
	trSource nvarchar(128) , -- 檢驗地目
	trValid char(1) default '1' , -- 1 有效 2 無效
	trDeptId int ,  -- 單位(deptSeq) 
);
    
create table psTestReportDetail (
	rid int  primary key ,  -- psTestReport.sid 
	trSeq nvarchar(50) , -- 報告編號
	trdDate datetime ,  -- 報告日期
	trdFIleName nvarchar(50)  ,			-- 報告檔案
	trdIrrContext  nvarchar(200)-- 農藥使用違規內容
)

create table psTestResult (
	rid int IDENTITY(1,1) primary key , 
	trId int  , -- psTestReport.sid 
	tcId int ,
	[tiTestCHTName] [nvarchar](50) NULL,
	[tiTestENGName] [nvarchar](50) NULL,
	[tiMethodLimit] [numeric](20, 4) NULL,
	tiLawCond char(1) default '1',  -- <= 1 , = 2
	[tiLawItem] [numeric](20, 0) NULL,
	tiNotAllow char(1) default '2' ,  -- 1 驗出 2不得驗出 
	tretResult nvarchar(50) -- 檢驗結果
)


create table psFarmer (
	caid int not null , 
	deptSeq int not null , 
	tmStatus char(1) default '1' , -- 1 :啟用 , 2 : 停用
	tmRowStatus char(1) default '1' , -- 1 新增修改 , -- 刪除
	primary key (caid , deptSeq)
);




CREATE TABLE [dbo].[ProcessCodeMain](
	[codeSeq] [int] IDENTITY(1,1) NOT NULL,
	[codeParent] [int] NOT NULL default 0,
	[codeValue] [nvarchar](200) NOT NULL,
	[codeShow] [nvarchar](200) NOT NULL,
	[codeSort] [int] NOT NULL default 0,
	[deptId] [int] NOT NULL,
	primary key (codeSeq)
 ); 
 
 
 CREATE TABLE [dbo].[psSmartCardRecord](
 [transactionID] VARCHAR(8) NOT NULL PRIMARY KEY, --商店交易序號
 [idNumber] VARCHAR(10) NOT NULL, --身份證字號
 [cardId] VARCHAR(10) NOT NULL,--卡號
 [merchantID] VARCHAR(10) NULL, --商店代碼(因舊版燒卡程式的問題造成商店號碼沒寫入)
 [pesticideID] VARCHAR(12) NOT NULL, --農藥商品代碼
 [transactionDate] DATETIME NOT NULL, --購買時間
 [transactionAmount]int NOT NULL, --購買數量
 [warmID] VARCHAR(10) NULL, --病蟲害代碼
 [tillageID] VARCHAR(10), NULL --作物代碼
 [createDate] DATETIME NOT NULL DEFAULT GETDATE()
);
CREATE TABLE [dbo].[psSmartCardRecord](
	[cardTransactionID] VARCHAR(18) PRIMARY KEY,--卡片交易號碼
	[transactionID] VARCHAR(8) NULL, --商店交易序號
	[persionNo] VARCHAR(10) NOT NULL, --身份證字號
	[cardNo] VARCHAR(10) NOT NULL,--卡號
	[storeID] CHAR(10) NULL, --商店代碼(因舊版燒卡程式的問題造成商店號碼沒寫入)
	[barcode] CHAR(13) NOT NULL, --農藥商品條碼
	[purchaseDT] CHAR(14) NOT NULL, --購買時間
	[purchaseQty]int NOT NULL, --購買數量
	[pestID] VARCHAR(10) NULL, --病蟲害代碼
	[cropID] VARCHAR(10) NULL, --作物代碼 
	[createDate] DATETIME NOT NULL DEFAULT GETDATE(),
	[checked] CHAR(1) NOT NULL DEFAULT 'N'
);
