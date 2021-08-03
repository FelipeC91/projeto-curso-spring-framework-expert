
INSERT INTO usuario (nome, email, senha, ativo)  VALUES('Maria Pereira', 'maria_pereira@brewer.com', '$2a$10$TXknCUivQXXBZdMguw2nEeVTavg6ATxfV1D6IA.8EgrjVrkysRysS', 1);
INSERT INTO usuario(nome, email, senha, ativo)  VALUES('Pedro Silva', 'pedro_silva@brewer.com', '$2a$10$TXknCUivQXXBZdMguw2nEeVTavg6ATxfV1D6IA.8EgrjVrkysRysS', 1);

INSERT INTO usuario_grupo(codigo_usuario, codigo_grupo)  VALUES(6, 2);
INSERT INTO usuario_grupo(codigo_usuario, codigo_grupo)  VALUES(7, 2);
INSERT INTO usuario_grupo(codigo_usuario, codigo_grupo)  VALUES(7, 1);