--
-- Table structure for table `IBISTEMP`
--
CREATE TABLE `IBISTEMP` (
                            `TKEY` int NOT NULL,
                            `TINT` int DEFAULT NULL,
                            `TCHAR` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `TVARCHAR` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `TNUMBER` decimal(17,5) DEFAULT NULL,
                            `TDATETIME` datetime DEFAULT NULL,
                            `TTIMESTAMP` timestamp NULL DEFAULT NULL,
                            `TDATE` date DEFAULT NULL,
                            `TTIME` time DEFAULT NULL,
                            `TBLOB` longblob,
                            `TCLOB` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for table `IBISTEMP`
--
ALTER TABLE `IBISTEMP`
    ADD PRIMARY KEY (`TKEY`);

--
-- AUTO_INCREMENT for table `IBISTEMP`
--
ALTER TABLE `IBISTEMP`
    MODIFY `TKEY` int NOT NULL AUTO_INCREMENT;
COMMIT;
