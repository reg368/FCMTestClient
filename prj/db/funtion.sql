xx
-- function
create FUNCTION [dbo].[SplitStrings]
(
    @List       NVARCHAR(MAX),
    @Delimiter  NVARCHAR(255)
)
RETURNS TABLE
AS
    RETURN (SELECT Number = ROW_NUMBER() OVER (ORDER BY Number),
        Item FROM (SELECT Number, Item = LTRIM(RTRIM(SUBSTRING(@List, Number, 
        CHARINDEX(@Delimiter, @List + @Delimiter, Number) - Number)))
    FROM (SELECT ROW_NUMBER() OVER (ORDER BY s1.[object_id])
        FROM sys.all_objects AS s1 CROSS APPLY sys.all_objects) AS n(Number)
    WHERE Number <= CONVERT(INT, LEN(@List))
        AND SUBSTRING(@Delimiter + @List, Number, 1) = @Delimiter
    ) AS y);

    

create FUNCTION [dbo].[LastIndex](@STRING VARCHAR(8000), @CHAR CHAR)
RETURNS INT
AS
BEGIN
DECLARE @INDEX int,
@START int
SELECT @STRING = RTRIM(LTRIM(@STRING))
SELECT @START = 0
SELECT @INDEX = CHARINDEX(@CHAR, @STRING, @START)
WHILE @INDEX <> 0
BEGIN
SELECT @START = @INDEX
SELECT @INDEX = CHARINDEX(@CHAR, @STRING, @START+1)
END
RETURN (@START)
END;

create PROCEDURE [dbo].[sp_getIdentity] (@name NVARCHAR(15))
AS
BEGIN
 DECLARE @i AS BIGINT; 
 UPDATE Seq SET @i = val = val +1 WHERE name=@name
 SELECT @i
END;


create view dbo.CardApplyView as
select a.* , b.deptSeq , tmStatus , tmRowStatus , 
(SELECT deptName FROM dbo.Dept AS c WHERE c.deptSeq = b.deptSeq ) AS DeptIdText,
(SELECT deptHierarchyCode FROM dbo.Dept AS c WHERE c.deptSeq = b.deptSeq ) AS DeptIdPath,
(CASE WHEN status IN (3, 4, 5) THEN 1 ELSE 2 END) as CardStatus , 
(select cityName from CitySet_t where cityId = a.cityId_R) cityName , 
(select zipCountyName from ZipSet_t where zipCode = a.distId_R) zipName 
from  CardApply_T a left join psFarmer b on ( a.caid = b.caid) ; 





SELECT a.* ,  b.tiCropId , b.licNo , 
	(select case when count(*)>0 then '是' else '否' end from get_testReportPesticide(a.rid) c 
	where ( c.licno = b.licno or c.pesticideName = a.tiTestCHTName or c.pesticideEName = a.tiTesTENGName)) as buyrec ,
	(case when ( b.tiLawCond = 1 and cast(a.tretResult as numeric) < a.tiLawItem)  then 1 
			when ( b.tiLawCond = 2 and cast(a.tretResult as numeric) <= a.tiLawItem) then 1 else 2 end) as allow
FROM dbo.psTestResult  a INNER JOIN dbo.psTestItem  b ON a.tcId = b.tcId




SELECT rid, trTestDate, trSCropDate, trECropDate, tcId, cat1, cat2, cat3, trTestOrgId, trSeq, trFarmerId, trSource, trValid, trDeptId,
(SELECT	toName FROM dbo.psTestOrg WHERE (toId = a.trTestOrgId)) AS toname,
(SELECT deptName FROM dbo.Dept WHERE (deptSeq = a.trDeptId)) AS deptname,
(SELECT tcCropName FROM dbo.psTestCropView WHERE (tcId = a.tcId)) AS cropname, 
dbo.fun_getFarmerName(trFarmerId) AS names, 
(select ltrim(cat1+cat2+cat3) from psTestCrop where tcId = a.tcId) AS cropid, 
GETDATE() AS trdate ,
((select codeshow from ProcessCodeMain where codeSeq = a.cat1) + 
isnull((select case when (codeShow is not null) then '-'+codeShow end  from ProcessCodeMain where codeSeq = a.cat2 ),'') +
isnull((select case when (codeShow is not null) then '-'+codeShow end  from ProcessCodeMain where codeSeq = a.cat3 ),'')) as cat
FROM dbo.psTestReport AS a



SELECT          a.id, a.storeid, a.pesticideId, a.cropId, a.pestId, a.barcode, a.purchaseQty, a.salesType, a.custCardNo, a.importDT, 
                            b.licType, b.licNo, CONVERT(datetime, SUBSTRING(a.purchaseDT, 1, 8), 102) AS purchaseDT,
                                (SELECT          name
                                  FROM               dbo.Crop_T AS c
                                  WHERE           (code = a.cropId)) AS cropIdText,
                                (SELECT          name
                                  FROM               dbo.Pest_T AS d
                                  WHERE           (code = a.pestId)) AS pestIdText,
                                (SELECT          brandName
                                  FROM               dbo.PesticideLic_T AS e
                                  WHERE           (licType = b.licType) AND (licNo = b.licNo)) AS brandName,
                                (SELECT          pesticideName
                                  FROM               dbo.PesticideLic_T AS e
                                  WHERE           (licType = b.licType) AND (licNo = b.licNo)) AS pesticideName ,
                                (SELECT          domManufName
                                  FROM               dbo.PesticideLic_T AS e
                                  WHERE           (licType = b.licType) AND (licNo = b.licNo)) AS domManufName  
FROM              dbo.Sales_T AS a INNER JOIN
                            dbo.Barcode_T AS b ON a.barcode = b.barcode

