DELETE FROM item_pedido;
DELETE FROM pedido;
DELETE FROM produto;
DELETE FROM restaurante;

ALTER TABLE restaurante ALTER COLUMN id RESTART WITH 1;
ALTER TABLE produto ALTER COLUMN id RESTART WITH 1;

INSERT INTO restaurante (nome, categoria, telefone, taxa_entrega, tempo_entrega_minutos, ativo) VALUES
('Pizzaria Almeida', 'Massa', '0800555760', 5.00, 120, true),
('Burger House', 'Hamburgueria', '11999990002', 7.50, 40, true),
('Sushi Prime', 'Japonesa', '11999990003', 8.90, 50, true),
('Taverna Davenport', 'Comida Colonial', '11999990010', 7.50, 45, true),
('Estalagem Auditore', 'Italiana', '11999990011', 6.50, 35, true),
('Taberna Black Flag', 'Frutos do Mar', '11999990012', 9.90, 55, true);

INSERT INTO produto (nome, categoria, descricao, preco, disponivel, restaurante_id) VALUES
('Pizza Calabresa', 'Pizza', 'Pizza de calabresa com cebola e mussarela', 45.90, true, 1),
('Pizza Portuguesa', 'Pizza', 'Pizza com presunto, ovos, cebola e ervilha', 52.90, true, 1),
('Burger Clássico', 'Hamburguer', 'Pão, carne bovina, queijo e alface', 24.90, true, 2),
('Burger Bacon', 'Hamburguer', 'Hambúrguer artesanal com bacon crocante', 29.90, true, 2),
('Combo Sushi 20 Peças', 'Sushi', 'Combinado com sashimi, nigiri e uramaki', 54.90, true, 3),
('Temaki Salmão', 'Temaki', 'Temaki de salmão com cream cheese', 28.90, true, 3),
('Ensopado Colonial', 'Prato Principal', 'Ensopado servido na Homestead Davenport', 42.90, true, 4),
('Torta Davenport', 'Sobremesa', 'Torta colonial servida na taverna Davenport', 24.90, true, 4),
('Massa Auditore', 'Massa', 'Massa italiana inspirada na família Auditore', 39.90, true, 5),
('Vinho de Monteriggioni', 'Bebida', 'Bebida especial da Estalagem Auditore', 18.90, true, 5),
('Peixe Black Flag', 'Frutos do Mar', 'Peixe grelhado da tripulação Black Flag', 49.90, true, 6),
('Rum do Capitão', 'Bebida', 'Bebida temática da Taberna Black Flag', 21.90, true, 6);