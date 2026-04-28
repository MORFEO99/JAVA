-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-11-2025 a las 08:32:03
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `control_asistencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registros_asistencia`
--

CREATE TABLE `registros_asistencia` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT current_timestamp(),
  `tipo` enum('entrada','salida') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `registros_asistencia`
--

INSERT INTO `registros_asistencia` (`id`, `usuario_id`, `fecha_hora`, `tipo`) VALUES
(1, 2, '2025-11-17 00:49:40', 'entrada'),
(2, 2, '2025-11-17 00:49:40', 'salida'),
(3, 1, '2025-11-17 00:49:50', 'entrada'),
(4, 1, '2025-11-17 00:49:50', 'salida'),
(5, 1, '2025-11-17 00:50:15', 'salida'),
(6, 1, '2025-11-17 00:50:15', 'entrada'),
(7, 2, '2025-11-17 00:50:44', 'salida'),
(8, 2, '2025-11-17 00:50:44', 'entrada'),
(9, 1, '2025-11-17 00:50:56', 'entrada'),
(10, 1, '2025-11-17 00:50:56', 'salida'),
(11, 1, '2025-11-17 00:51:20', 'entrada'),
(12, 1, '2025-11-17 00:51:20', 'salida'),
(13, 1, '2025-11-17 00:52:41', 'salida'),
(14, 1, '2025-11-17 00:52:42', 'entrada'),
(15, 1, '2025-11-17 00:53:55', 'salida'),
(16, 1, '2025-11-17 00:53:55', 'entrada'),
(17, 1, '2025-11-17 00:54:14', 'salida'),
(18, 1, '2025-11-17 00:54:14', 'entrada'),
(19, 2, '2025-11-17 01:00:40', 'entrada'),
(20, 1, '2025-11-17 01:00:47', 'entrada'),
(21, 2, '2025-11-17 01:01:04', 'salida'),
(22, 1, '2025-11-17 01:01:12', 'salida'),
(23, 2, '2025-11-17 01:02:30', 'entrada'),
(24, 1, '2025-11-17 01:02:39', 'entrada'),
(25, 2, '2025-11-17 01:04:51', 'salida'),
(26, 1, '2025-11-17 01:04:54', 'salida'),
(27, 2, '2025-11-17 01:08:30', 'entrada'),
(28, 1, '2025-11-17 01:08:40', 'entrada'),
(29, 1, '2025-11-17 01:09:51', 'salida'),
(30, 2, '2025-11-17 01:09:56', 'salida'),
(31, 1, '2025-11-17 01:10:06', 'entrada'),
(32, 2, '2025-11-17 01:10:11', 'entrada'),
(33, 2, '2025-11-17 01:11:03', 'salida'),
(34, 1, '2025-11-17 01:11:08', 'salida'),
(35, 2, '2025-11-17 01:11:14', 'entrada'),
(36, 1, '2025-11-17 01:11:29', 'entrada'),
(37, 2, '2025-11-17 01:13:31', 'salida'),
(38, 1, '2025-11-17 01:13:46', 'salida'),
(39, 2, '2025-11-17 01:21:29', 'entrada'),
(40, 1, '2025-11-17 01:21:29', 'entrada'),
(41, 2, '2025-11-17 01:22:50', 'salida'),
(42, 1, '2025-11-17 01:22:50', 'salida'),
(43, 2, '2025-11-17 01:22:50', 'entrada'),
(44, 2, '2025-11-17 01:23:50', 'entrada'),
(45, 1, '2025-11-17 01:24:51', 'entrada'),
(46, 2, '2025-11-17 01:24:51', 'salida'),
(47, 1, '2025-11-17 01:25:51', 'salida'),
(48, 1, '2025-11-17 01:26:51', 'entrada'),
(49, 2, '2025-11-17 01:26:51', 'entrada'),
(50, 2, '2025-11-17 01:28:55', 'salida'),
(51, 1, '2025-11-17 01:28:55', 'salida'),
(52, 1, '2025-11-17 01:29:56', 'entrada'),
(53, 2, '2025-11-17 01:29:56', 'entrada'),
(54, 2, '2025-11-17 01:31:23', 'salida'),
(55, 1, '2025-11-17 01:31:23', 'salida'),
(56, 1, '2025-11-17 01:33:59', 'entrada'),
(57, 2, '2025-11-17 01:33:59', 'entrada'),
(58, 1, '2025-11-17 01:34:59', 'salida'),
(59, 2, '2025-11-17 01:34:59', 'salida'),
(60, 2, '2025-11-17 01:36:58', 'entrada'),
(61, 1, '2025-11-17 01:36:58', 'entrada'),
(62, 2, '2025-11-17 01:36:58', 'salida'),
(63, 1, '2025-11-17 01:36:58', 'salida'),
(64, 2, '2025-11-17 01:45:14', 'entrada'),
(65, 1, '2025-11-17 01:45:21', 'entrada'),
(66, 2, '2025-11-17 01:45:31', 'salida'),
(67, 1, '2025-11-17 01:45:39', 'salida'),
(68, 2, '2025-11-17 01:45:51', 'entrada'),
(69, 1, '2025-11-17 01:45:56', 'entrada'),
(70, 2, '2025-11-17 01:47:21', 'salida'),
(71, 1, '2025-11-17 01:47:30', 'salida'),
(72, 2, '2025-11-17 01:48:27', 'entrada'),
(73, 1, '2025-11-17 01:48:40', 'entrada'),
(74, 1, '2025-11-17 01:49:49', 'salida'),
(75, 2, '2025-11-17 01:49:53', 'salida'),
(76, 1, '2025-11-17 01:50:04', 'entrada'),
(77, 2, '2025-11-17 01:50:12', 'entrada'),
(78, 2, '2025-11-17 01:52:28', 'salida'),
(79, 1, '2025-11-17 01:52:35', 'salida'),
(80, 1, '2025-11-17 03:26:10', 'entrada'),
(81, 2, '2025-11-17 03:26:14', 'entrada'),
(82, 1, '2025-11-17 03:26:28', 'salida'),
(83, 2, '2025-11-17 03:26:36', 'salida'),
(84, 2, '2025-11-17 03:26:47', 'entrada'),
(85, 1, '2025-11-17 03:26:52', 'entrada'),
(86, 2, '2025-11-17 03:28:24', 'salida'),
(87, 1, '2025-11-17 03:28:28', 'salida'),
(88, 1, '2025-11-17 03:28:38', 'entrada'),
(89, 2, '2025-11-17 03:28:45', 'entrada'),
(90, 2, '2025-11-17 03:28:57', 'salida'),
(91, 1, '2025-11-17 03:30:54', 'salida'),
(92, 2, '2025-11-17 03:30:59', 'entrada'),
(93, 1, '2025-11-17 03:31:06', 'entrada'),
(94, 1, '2025-11-17 03:31:14', 'salida'),
(95, 2, '2025-11-17 03:31:20', 'salida');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `uid` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `activo` tinyint(1) DEFAULT 1,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `uid`, `nombre`, `activo`, `fecha_registro`) VALUES
(1, '3C7A2B39', 'Joel', 1, '2025-11-17 03:31:04'),
(2, 'A2845551', 'Dario Tola', 1, '2025-11-17 03:31:04');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `registros_asistencia`
--
ALTER TABLE `registros_asistencia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id` (`usuario_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uid` (`uid`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `registros_asistencia`
--
ALTER TABLE `registros_asistencia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `registros_asistencia`
--
ALTER TABLE `registros_asistencia`
  ADD CONSTRAINT `registros_asistencia_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
