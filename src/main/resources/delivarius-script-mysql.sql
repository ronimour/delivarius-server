INSERT INTO delivarius.product
(name,price,picture,description)
VALUES
('Coca-cola 2L',4.79,'coca_cola_2l.png','Cola-cola em garrafa de plástico com 2 litros'),
('Pão de forma integral Pullman 550G',6.25,'pao_forma_integral_pullman_550g.png','pão de forma integral da marca Pullman, pacote com 10 fatis, pesando 550 gramas ao todo'),
('Carvão Cariocão saco 5kg',8.12,'carvao_cariocao.png','Saco de carvão pronto para churrasco, com 5 kilogramas'),
('Gelo Qualitá 5kg',3.6,'gelo_qualita_5kg.png','Saco de gelo 5 kilogramas'),
('Agua mineral Hydrate 20L',6,'agua_mineral_hydrate_20l.png','Agua mineral em garrafão de plástico com 20 litros. Obs.: vasilhame não incluso'),
('Agua mineral Fontes Debelém 20L',8,'agua_mineral_fontes_debelem_20l.png','Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso'),
('Agua mineral Indaiá 20L',7,'agua_mineral_indaia_20l.png','Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso'),
('Agua mineral Ingá 20L',6,'agua_mineral_inga_20l.png','Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso'),
('Agua mineral Sarandí 20L',5,'agua_mineral_sarandi_20l.png','Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso'),
('Agua mineral Santa Maria 20L',5,'agua_mineral_santa_maria_20l.png','Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso'),
('Cerveja Itaipava lata 350ML',3.5,'cerveja_itaipava_lata_350ml.png','Cerveja em lata Itaipava, unidade'),
('Cerveja Antartica lata 350ML',3,'cerveja_antartica_lata_350ml.png','Cerveja em lata Itaipava, unidade'),
('Cerveja Brahma lata 350ML',2.5,'cerveja_brahma_lata_350ml.png','Cerveja em lata Brahma, unidade'),
('Cerveja Budweiser lata 350ML',5,'cerveja_budweiser_lata_350ml.png','Cerveja em lata Budweiser, unidade'),
('Cerveja Devassa lata 269ML',4,'cerveja_devassa_lata_269ml.png','Cerveja em lata Devassa, unidade'),
('Cerveja Eisebahn lata 350ML',6,'cerveja_eisenbahn_lata_350ml.png','Cerveja em lata Eisenbahn, unidade'),
('Cerveja Heineken lata 350ML',6,'cerveja_heineken_lata_350ml.png','Cerveja em lata Heineken, unidade'),
('Cerveja Skol lata 350ML',3.5,'cerveja_skol_lata_350ml.png','Cerveja em lata Skol, unidade');


INSERT INTO delivarius.address
(id,street,zip_code)
VALUES
(1,'Av. São Miguel dos Caribés, 8 - Neópolis, Natal - RN, 59088-500','59088-500'),
(2,'Av. Dão Silveira, 3712 - Candelária, Natal - RN, 59066-180','59066-180'),
(3,'Av. Prudente de Morais, 6399 - Lagoa Nova, Natal - RN, 59064-630','59064-630');

INSERT INTO delivarius.store
(id,address_id,name,description,picture,opens24hours,registration_date)
VALUES
(1,1,'Conveniência Gelo e Gela 24 horas','Conveniência que funciona 24 horas, para tomar aquela gela. Telefone: (84) 2020-8930. ','conveniencia_gelo_e_gela_24h.png',true,current_timestamp()),
(2,2,'Super Conveniência Horizonte','Conveniência que funciona todos os dias, das 06 às 22 horas. Telefone: (84) 3217-7778. ','super_conveniencia_horizonte.png',false,current_timestamp()),
(3,3,'Empório Conveniência 24h','Conveniência que funciona 24 horas. Telefone: (84) 2010-8252. ','emporio_conveniencia_24h.png', true, current_timestamp());


INSERT INTO delivarius.store_products
(store_id,products_id)
VALUES
(1,1),(1,2),(1,3),(1,4),
(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),
(3,11),(3,12),(3,13),(3,14),(3,15),(3,16),(3,17),(3,18);
