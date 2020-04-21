--# PerfilAcesso        (id,nome);
--# Usuario             (id,email,senha,celular,habilitado,perfil);
--# Pessoa              (id,nome,dataNascimento,USUARIO);
--# Cliente             (id,PESSOA);
--# Funcionario         (id,PESSOA);
--# CategoriaServico    (id,nome,descricao,categoriaPai);
--# Servico             (id,nome,descricao,valor,CATEGORIA,tempo,intervalo,funcionarios[],camposadicionais[]);
--# CampoAdicional      (id,nome,descricao,tipo)
--# TipoCampoAdicional  (id,nome,)
--# SQL - Tabela

drop schema sistema cascade;
create schema sistema;
set schema 'sistema';

create table PerfilAcesso(
  id integer,
  nome varchar(50),
  constraint pkPerfilAcesso primary key (id)
);

create table Usuario(
  id integer,
  email varchar(100) not null,
  senha varchar(500) not null,
  celular bigint not null,
  perfil integer not null,
  habilitado boolean default TRUE NOT NULL,
  constraint pkUsuario primary key (id),
  constraint unqUsuario unique (email),
  constraint unqCelularUsuario unique (celular),
  constraint fkPerfilAcesso foreign key (perfil) references PerfilAcesso(id)
);

create table Pessoa(
  id              integer,
  nome            varchar(100) not null,
  dataNascimento  date not null,
  usuario		  integer,
  constraint pkPessoa primary key (id),
  constraint fkUsuario     foreign key (usuario)      references Usuario(id)
);

create table Cliente(
  id integer,
  pessoa integer,
  constraint pkCliente          primary key (id),
  constraint unqPessoaCliente   unique (pessoa),
  constraint fkPessoa           foreign key (pessoa)    references Pessoa(id)
);

create table Funcionario(
  id integer,
  pessoa integer,
  constraint pkFuncionario    primary key (id),
  constraint unqFuncionario   unique (pessoa),
  constraint fkPessoa         foreign key (pessoa)    references Pessoa(id)
);


--##########################

create table CategoriaServico(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500) default '' not null,
  categoriaPai integer default 0,
  constraint pkCategoriaServico primary key (id),
  constraint unqNomeCategoria unique (nome,categoriaPai),
  constraint fkCategoriaPai foreign key (categoriaPai) references CategoriaServico(id)
);

create table Servico(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500),
  valor money,
  duracao time, --select cast('11:00 PM' as time);
  intervalo interval, --select cast('11:00 PM' as time);
  categoria integer,
  funcionarios integer[],
  camposadicionais integer[],
  constraint pkServico primary key (id),
  constraint unqNomeServico unique (nome),
  constraint fkCategoria foreign key (categoria) references CategoriaServico(id)
);

create table TipoCampoAdicional(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500),
  constraint pkTipoCampoAdicional primary key (id),
  constraint unqNomeTipoCampoAdicional unique (nome)
);

create table CampoAdicional(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500),
  tipo integer,
  constraint pkCampoAdicional primary key (id),
  constraint unqNomeCampoAdicional unique (nome),
  constraint fkTipo foreign key (tipo) references TipoCampoAdicional(id)
);

--###########
create sequence sistema.sqn_usuario;
create sequence sistema.sqn_servico;
create sequence sistema.sqn_cliente;
create sequence sistema.sqn_pessoa;
create sequence sistema.sqn_funcionario;
create sequence sistema.sqn_categoriaservico;
create sequence sistema.sqn_campoadicional;
create sequence sistema.sqn_tipocampoadicional;

--###########

insert into PerfilAcesso values
  (1,'CLIENTECOMUM'),
  (2,'FUNCIONARIOCOMUM'),
  (3,'FUNCIONARIOADMIN');

insert into Usuario values
  (nextval('sqn_usuario'),'admin@admin.com','YWRtaW4=',11912341234,3);

insert into CategoriaServico values
  (0,'DEFAULT','Todas categorias serão filhas dessas categoria.',null);
