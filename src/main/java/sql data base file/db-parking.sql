/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     30/04/2021 10:03:20                          */
/*==============================================================*/


drop table if exists Admin;

drop table if exists Compte;

drop table if exists Demande;

drop table if exists Guerite;

drop table if exists ResponsableSite;

drop table if exists Site;

drop table if exists SuperAdmin;

drop table if exists UtilisateurEntrerprise;

/*==============================================================*/
/* Table: Admin                                                 */
/*==============================================================*/
create table Admin
(
   id                   int not null,
   tel                  int,
   primary key (id)
);

/*==============================================================*/
/* Table: Compte                                                */
/*==============================================================*/
create table Compte
(
   id                   int not null AUTO_INCREMENT,
   nom                  varchar(254),
   prenom               varchar(254),
   login                varchar(254),
   password             varchar(254),
   poste                varchar(254),
   statutC              boolean,
   email                varchar(254),
   primary key (id)
);

/*==============================================================*/
/* Table: Demande                                               */
/*==============================================================*/
create table Demande
(
   numDemande           int not null AUTO_INCREMENT,
   id                   int not null,
   nomC                 varchar(254),
   prenomC              varchar(254),
   cnie                 varchar(254),
   dateVisite           datetime,
   dateFinVisite        datetime,
   personne             varchar(254),
   email                varchar(254),
   nbrPersonnes         int,
   statutD              boolean,
   primary key (numDemande)
);

/*==============================================================*/
/* Table: Guerite                                               */
/*==============================================================*/
create table Guerite
(
   id                   int not null,
   idSite               int not null,
   dateCreation         datetime,
   cin                  int,
   primary key (id)
);

/*==============================================================*/
/* Table: ResponsableSite                                       */
/*==============================================================*/
create table ResponsableSite
(
   id                   int not null,
   idSite               int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: Site                                                  */
/*==============================================================*/
create table Site
(
   nomSite              varchar(254),
   ville                varchar(254),
   entreprise           varchar(254),
   idSite               int not null AUTO_INCREMENT,
   id                   int not null,
   primary key (idSite)
);

/*==============================================================*/
/* Table: SuperAdmin                                            */
/*==============================================================*/
create table SuperAdmin
(
   id                   int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: UtilisateurEntrerprise                                */
/*==============================================================*/
create table UtilisateurEntrerprise
(
   id                   int not null,
   societe              varchar(254),
   fonction             varchar(254),
   tel                  int,
   active               bool,
   primary key (id)
);

alter table Admin add constraint FK_Generalization_1 foreign key (id)
      references Compte (id) on delete restrict on update restrict;

alter table Demande add constraint FK_Association_1 foreign key (id)
      references UtilisateurEntrerprise (id) on delete restrict on update restrict;

alter table Guerite add constraint FK_Association_3 foreign key (idSite)
      references Site (idSite) on delete restrict on update restrict;

alter table Guerite add constraint FK_Generalization_5 foreign key (id)
      references Compte (id) on delete restrict on update restrict;

alter table ResponsableSite add constraint FK_Association_4 foreign key (idSite)
      references Site (idSite) on delete restrict on update restrict;

alter table ResponsableSite add constraint FK_Generalization_2 foreign key (id)
      references Compte (id) on delete restrict on update restrict;

alter table Site add constraint FK_Association_2 foreign key (id)
      references Admin (id) on delete restrict on update restrict;

alter table SuperAdmin add constraint FK_Generalization_6 foreign key (id)
      references Admin (id) on delete restrict on update restrict;

alter table UtilisateurEntrerprise add constraint FK_Generalization_4 foreign key (id)
      references Compte (id) on delete restrict on update restrict;

