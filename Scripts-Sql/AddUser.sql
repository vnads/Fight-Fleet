/* For security reasons the login is created disabled and with a random password. */
/****** Object:  Login [FightFleet]    Script Date: 10/30/2012 17:27:49 ******/
CREATE LOGIN [FightFleet] WITH PASSWORD=N'''LJ=\;Q¢qZÃ<£"s÷aM òöfÛ[®®OÁ', DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO

ALTER LOGIN [FightFleet] DISABLE
GO


CREATE USER [FightFleet] FOR LOGIN [FightFleet] WITH DEFAULT_SCHEMA=[dbo]
GO
