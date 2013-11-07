DROP TABLE IF EXISTS mail;
CREATE TABLE mail (
  ID int(11) NOT NULL auto_increment,
  Sender varchar(45) NOT NULL default '',
  toRecipients varchar(255) NOT NULL default '',
  CcRecipients varchar(255) NOT NULL default '',
  BccRecipients varchar(255) NOT NULL default '',
  Subject varchar(100) NOT NULL default '',
  Content varchar(60000) NOT NULL default '',
  MessageDate date NOT NULL,
  FolderID int(11) NOT NULL default 1,
  PRIMARY KEY  (ID),
  FOREIGN KEY (FolderID) REFERENCES folder(ID)
);

DROP TABLE IF EXISTS contact;
CREATE TABLE contact (
  ID int(11) NOT NULL auto_increment,
  Email varchar(255) NOT NULL,
  Name varchar(45) NOT NULL default '',
  PhoneNum varchar(13) NOT NULL default '',
  PRIMARY KEY  (ID)
);

DROP TABLE IF EXISTS folder;
CREATE TABLE folder (
  ID int(11) NOT NULL auto_increment,
  Name varchar(45) NOT NULL,
  PRIMARY KEY  (ID)
);

Insert into folder(ID, Name) values (NULL, 'Inbox');
Insert into folder(ID, Name) values (NULL, 'Sent');
Insert into folder(ID, Name) values (NULL, 'toSend');

Insert into mail(ID, Sender, ToRecipients, CcRecipients, BccRecipients, Subject, content, MessageDate, folderID)  Values(NULL, "sender","ToRecipients", "CcRecipients", "BccRecipients","subject", "content", CURDATE(), 1);