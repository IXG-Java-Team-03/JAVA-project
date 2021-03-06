; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "@PROGRAMNAME@"
#define MyAppVersion "@PROGVERSION@"
#define MyAppPublisher "@PUBLISHERNAME@"
#define MyAppExeName "JAVA-project.cmd"
#define mainDir "@MAINDIRHOME@"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{838BFE53-4250-498E-AE6D-ECF5FB9534AD}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName=C:\JAVAteam\{#MyAppName}
DisableProgramGroupPage=yes
OutputDir={#mainDir}\binaries\output
OutputBaseFilename=WordGamesetup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; 

[Files]
Source: {#mainDir}\JAVA-project\packager\JAVA-project.cmd; DestDir: "{app}"; Flags: ignoreversion
Source: {#mainDir}\binaries\WordGame\dist\JAVA-project.jar; DestDir: "{app}"; Flags: ignoreversion
Source: {#mainDir}\JAVA-project\wooden.jpg; DestDir: "{app}"; Flags: ignoreversion
Source: {#mainDir}\binaries\WordGame\dist\libs\JAVA-project-preloader.jar; DestDir: "{app}\libs"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: {#mainDir}\binaries\WordGame\dist\libs\org.hamcrest.core_1.3.0.v20180420-1519.jar; DestDir: "{app}\libs"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: {#mainDir}\JAVA-project\words\*; DestDir: "{app}\words"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{commonprograms}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall skipifsilent

