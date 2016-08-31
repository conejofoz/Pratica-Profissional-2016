CREATE TABLE clientes
(
  id serial NOT NULL,
  nomecliente character varying(100),
  idcidade integer,
  email character varying(100),
  telefone character varying(20),
  sexo character(1),
  documento character varying(15),
  cpf character varying(15),
  cep character varying(15),
  endereco character varying(100),
  numero character(15),
  complemento character varying(50),
  telefonecomercial character varying(20),
  celular character varying(20),
  fax character varying(20),
  site character varying(100),
  tipopessoa character(1),
  bairro character varying(50),
  cnpj character varying(20),
  datanascimento date,
  CONSTRAINT clientes_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE clientes
  OWNER TO postgres;


-- Table: fornecedores

-- DROP TABLE fornecedores;

CREATE TABLE fornecedores
(
  id serial NOT NULL,
  nomefornecedor character varying(100),
  documento character varying(100),
  endereco character varying(100),
  idcidade integer,
  email character varying(100),
  telefone character varying(20),
  tipopessoa character(1),
  sexo character(1),
  cep character varying(15),
  numero character varying(15),
  complemento character varying(50),
  bairro character varying(50),
  telefonecomercial character varying(20),
  celular character varying(20),
  fax character varying(20),
  site character varying(100),
  cpf character varying(15),
  datanascimento date,
  cnpj character varying(20),
  CONSTRAINT fornecedores_pkey PRIMARY KEY (id),
  CONSTRAINT fornecedores_idcidade_fkey FOREIGN KEY (idcidade)
      REFERENCES cidades (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE fornecedores
  OWNER TO postgres;



-- Table: produtos

-- DROP TABLE produtos;

CREATE TABLE produtos
(
  id serial NOT NULL,
  nomeproduto character varying(100),
  codigobarras character varying(13),
  codigodofornecedor character varying(15),
  quantidadeporcaixa numeric,
  estoqueminimo numeric,
  estoquemaximo numeric,
  estoqueatual numeric,
  pesoneto numeric,
  pesobruto numeric,
  tamanho character varying(15),
  valorcompra numeric,
  icms numeric,
  iss numeric,
  ipi numeric,
  precocusto numeric,
  valoricms numeric,
  valoriss numeric,
  valoripi numeric,
  precomedio numeric,
  precovarejo numeric,
  precoatacado numeric,
  dataultimacompra date,
  dataultimavenda date,
  idgrupo integer,
  idfornecedor integer,
  idmarca integer,
  datacadastro timestamp without time zone,
  horacadastro time without time zone,
  siglaunidade character varying(2),
  idncm character varying(8),
  idcst character varying(3),
  idcfop character varying(4),
  CONSTRAINT produtos_pkey PRIMARY KEY (id),
  CONSTRAINT produtos_idgrupo_fkey FOREIGN KEY (idgrupo)
      REFERENCES grupos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT produtos_idmarca_fkey FOREIGN KEY (idmarca)
      REFERENCES marcas (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT produtos_siglaunidade_fkey FOREIGN KEY (siglaunidade)
      REFERENCES unidades (siglaunidade) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE produtos
  OWNER TO postgres;




