DXA Alpaca Forms
=====================

The DXA Alpaca forms modules is based on [Alpaca.JS](http://www.alpacajs.org/) forms and DXA.
Through the provided form designer extension, forms can be created directly inline from the SDL Web CMS GUI. The forms are managed as standard SDL Web CMS components and can be added to arbitrary DXA pages.
The forms are Javascript/JQuery based and can easily be skinned using the DXA HTML design mechanism.

The forms can submit data to a configurable end-point, such as a RESTful web service. The Forms DXA module also comes with a built-in controller that can dispatch form data to various handlers that can process the data, such as store into a database, send an e-mail, pass data to a CRM etc.

The Forms DXA modules also supports prevention of CSRF (Cross-Site Request Forgery) attacks.

Prerequisites
----------------

DXA 1.7 and SDL Web 8/8.5. Older versions of DXA should possible to use with some minor tweaks
in the C#/Java dependencies.

Install
--------

1. Import the [Forms Content Porter package](https://raw.githubusercontent.com/NiclasCedermalm/dxa-alpaca-forms/master/cms/Forms-Module-v1.0.0.zip) into your CMS instance
2. Install the Alpaca Forms designer by unpacking the [Form Designer ZIP](https://raw.githubusercontent.com/NiclasCedermalm/dxa-alpaca-forms/master/cms/FormBuilder.zip) under %SDLWEB_HOME%/web/
3. Convert it to an application in the IIS console
4. Update the definition for the Form schema
    - Update the custom URL for the metadata field 'formDefinition':
      `http://<CMSHOST>/FormBuilder/index.html`
5. Define XPM content type(s) for forms (optional)
6. Republish the 'Publish Settings' & 'Publish HTML Design' pages and issue '/admin/refresh' to update the definitions in DXA
7. Install the DXA modules to your DXA:
    - .NET: Unzip the [DXA modules ZIP file](https://raw.githubusercontent.com/NiclasCedermalm/dxa-alpaca-forms/master/dotnet/compiled/AlpacaFormsModules-v1.0.0.zip) under your DXA root
    - Java: Compile the Java and install it your local Maven repository. Add the following to your webapp's pom.xml:

    ```
    <dependency>
        <groupId>com.sdl.dxa.modules.alpacaforms</groupId>
        <artifactId>alpaca-forms-dxa-module</artifactId>
        <version>1.0.0</version>
    </dependency>
    ```

Database Forms
----------------

As a reference for a Form handler a database handler has been created that stores submitted forms
into a simple DB. In addition there are some basic admin pages to view the submitted forms and its data.
The DB handler is available both for Java and .NET.

Setup:
1. Create the tables below in a new or existing database
2. Compile the code
  - .NET: Deploy DDLs and defined MVC Areas to your DXA.NET instance (provided in the ZIP above)
  - Java: add db handler to your webapp's pom.xml:

    ```
    <dependency>
        <groupId>com.sdl.dxa.modules.alpacaforms</groupId>
        <artifactId>alpaca-db-forms-handler</artifactId>
        <version>1.0.0</version>
    </dependency>
    ```

3. Configure the database access:
  - .NET Web.config, example:

   ```
   <!-- Alpaca Forms DB Connection -->
	 <add key="alpacaforms-db-connection" value="Data Source=(local);Initial Catalog=forms_db;User ID=FormUser;Password=alpaca"/>
   ```

   - Java dxa.properties, example using MS-SQL:

   ```
   form.db.jdbcUrl=jdbc:sqlserver://localhost:1433;databaseName=form_db;user=FormUser;password=alpaca
   form.db.jdbcDriverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
   ```

4. After restarting/redeploying your DXA instance you are ready to create DB forms
5. When creating forms in the CMS set the metadata field 'Form Handler' to 'DB-Form' to address the database form handler.
6. All submitted form data are available at http://[DXA HOST]/form/admin/list

Table Creation SQL:
(This is made for MS-SQL but can easily be modified for other RDMBS)
```
CREATE TABLE SUBMITTED_FORM(
	ID int NULL,
	FORM_ID varchar(20) NULL,
	SUBMIT_DATE date NULL
)
GO

CREATE TABLE SUBMITTED_FORM_FIELD(
	ID int NULL,
	NAME varchar(100) NULL,
	VALUE varchar(4000) NULL
)
GO

CREATE SEQUENCE FORM_ID_SEQUENCE
 AS int
 START WITH 1
 INCREMENT BY 1
GO
```

Future enhancements
---------------------

Below are some enhancements to be consider in future versions:
* Multi-step forms
* Improved forms designer with a SDL Web look&feel
* XPM inline editor

Branching model
----------------

We intend to follow Gitflow (http://nvie.com/posts/a-successful-git-branching-model/) with the following main branches:

 - master - Stable
 - develop - Unstable
 - release/x.y - Release version x.y

Please submit your pull requests on develop. In the near future we intend to push our changes to develop and master from our internal repositories, so you can follow our development process.


License
---------
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
