CREATE TABLE 
dbo.[DeviceInfo]
(
   [seq] int  NOT NULL IDENTITY  PRIMARY KEY,   --流水號
   [userid] nvarchar(50) NULL,
   [password] nvarchar(50) NULL,
   [clientToken] nvarchar(max) NULL ,           --client 裝置 token
   [deviceToken] nvarchar(max) NULL,            --device 裝置 token
   [clientPaltform] nvarchar(10) NULL,			--client 平台
   [packageName] nvarchar(50) NULL,             --專案名稱	
   [createDate] datetime NULL,                  --建立時間
   [updateDate] datetime NULL,					--修改時間
   [lastsendDate]  datetime NULL,				--最後傳送時間
   [lastsendToken]  nvarchar(max) NULL				--最後傳送時間
) 