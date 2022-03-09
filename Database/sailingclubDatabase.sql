-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 09, 2022 alle 18:30
-- Versione del server: 10.4.22-MariaDB
-- Versione PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sailingclub`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `boats`
--

CREATE TABLE `boats` (
  `idBoat` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `fcOwner` varchar(16) NOT NULL,
  `length` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `boats`
--

INSERT INTO `boats` (`idBoat`, `name`, `fcOwner`, `length`) VALUES
(3, 'La Pinta', 'a', 34221),
(5, 'babbo', 'a', 2312),
(6, 'BarcaDeRio', 'a', 232312),
(7, 'giovannaDarco', 'a', 65432),
(8, 'Gianni Morandi', 'a', 34212),
(9, 'Pappa Molla', 'a', 54321);

-- --------------------------------------------------------

--
-- Struttura della tabella `competition`
--

CREATE TABLE `competition` (
  `idCompetition` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `price` int(11) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `competition`
--

INSERT INTO `competition` (`idCompetition`, `name`, `price`, `date`) VALUES
(1, 'Asparagi Cup', 54, '2022-07-14'),
(2, 'Competizione matta Cup', 14, '2022-05-30'),
(3, 'Corri coi pesci', 14, '2022-08-11'),
(4, 'Sailing Club Top Cup', 110, '2022-06-20'),
(5, 'SIUM cup', 32, '2022-09-01'),
(6, 'Gara delle Gare', 200, '2022-08-01'),
(7, 'Cannavacciuolo Cup', 34, '2022-06-12'),
(8, 'Sassari cup', 54, '2022-05-20'),
(9, 'Boat Race', 23, '2022-07-06'),
(10, 'Garone', 25, '2022-07-14');

-- --------------------------------------------------------

--
-- Struttura della tabella `partner`
--

CREATE TABLE `partner` (
  `fiscalCode` varchar(16) NOT NULL,
  `password` varchar(20) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `address` varchar(45) NOT NULL,
  `isAdmin` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `partner`
--

INSERT INTO `partner` (`fiscalCode`, `password`, `name`, `surname`, `address`, `isAdmin`) VALUES
('a', 'a', '1', 'a', 'a', 0),
('admin', 'admin', 'admin', 'admin', 'via admin', 1),
('b', 'b', 'b', 'b', 'b', 0),
('c', 'c', 'c', 'c', 'c', 0),
('d', 'd', 'd', 'd', 'd', 0),
('w', 'w', 'w', 'w', 'w', 0),
('z', 'z', 'z', 'z', 'z', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `payments`
--

CREATE TABLE `payments` (
  `idPayment` int(11) NOT NULL,
  `fcPartner` varchar(16) NOT NULL,
  `deadLine` varchar(10) NOT NULL,
  `description` text NOT NULL,
  `amount` float NOT NULL,
  `paymentDate` varchar(10) DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `payments`
--

INSERT INTO `payments` (`idPayment`, `fcPartner`, `deadLine`, `description`, `amount`, `paymentDate`, `state`) VALUES
(1, 'a', '2022-02-23', 'Iscrizione Annuale', 135, '2022-02-14', 1),
(3, 'a', '2022-05-30', 'Iscrizione a Competizione matta Cup', 14, '2022-02-15', 1),
(4, 'a', '2022-08-20', 'Iscrizione a prova', 34, '2022-02-16', 1),
(5, 'a', '2022-07-14', 'Iscrizione a Asparagi Cup', 54, '2022-02-18', 1),
(6, 'a', '2022-06-12', 'Iscrizione a Cannavacciuolo Cup', 34, '2022-02-18', 1),
(7, 'a', '2022-03-12', 'Iscrizione a prova prova', 34, NULL, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `registrations`
--

CREATE TABLE `registrations` (
  `idRegistration` int(11) NOT NULL,
  `fcPartner` varchar(16) NOT NULL,
  `fkCompetition` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `registrations`
--

INSERT INTO `registrations` (`idRegistration`, `fcPartner`, `fkCompetition`) VALUES
(7, 'a', 6),
(8, 'a', 2),
(9, 'a', 1),
(10, 'a', 7),
(11, 'z', 10);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `boats`
--
ALTER TABLE `boats`
  ADD PRIMARY KEY (`idBoat`),
  ADD KEY `fkowner` (`fcOwner`);

--
-- Indici per le tabelle `competition`
--
ALTER TABLE `competition`
  ADD PRIMARY KEY (`idCompetition`);

--
-- Indici per le tabelle `partner`
--
ALTER TABLE `partner`
  ADD PRIMARY KEY (`fiscalCode`);

--
-- Indici per le tabelle `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`idPayment`),
  ADD KEY `fk_partner` (`fcPartner`);

--
-- Indici per le tabelle `registrations`
--
ALTER TABLE `registrations`
  ADD PRIMARY KEY (`idRegistration`),
  ADD KEY `fk_fcPartner` (`fcPartner`),
  ADD KEY `fk_competition` (`fkCompetition`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `boats`
--
ALTER TABLE `boats`
  MODIFY `idBoat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT per la tabella `competition`
--
ALTER TABLE `competition`
  MODIFY `idCompetition` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT per la tabella `payments`
--
ALTER TABLE `payments`
  MODIFY `idPayment` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT per la tabella `registrations`
--
ALTER TABLE `registrations`
  MODIFY `idRegistration` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `boats`
--
ALTER TABLE `boats`
  ADD CONSTRAINT `fkowner` FOREIGN KEY (`fcOwner`) REFERENCES `partner` (`fiscalCode`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `fk_partner` FOREIGN KEY (`fcPartner`) REFERENCES `partner` (`fiscalCode`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `registrations`
--
ALTER TABLE `registrations`
  ADD CONSTRAINT `fk_competition` FOREIGN KEY (`fkCompetition`) REFERENCES `competition` (`idCompetition`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_fcPartner` FOREIGN KEY (`fcPartner`) REFERENCES `partner` (`fiscalCode`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
