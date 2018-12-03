; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Word Game"
#define MyAppVersion "0.1"
#define MyAppPublisher "IXG java team 3"
#define MyAppExeName "JAVA-project.cmd"

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
OutputDir=C:\Users\nkot\Documents\eclipse-workspace\binaries\output
OutputBaseFilename=WordGamesetup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; 

[Files]
Source: "C:\Users\nkot\Documents\eclipse-workspace\JAVA-project\packager\JAVA-project.cmd"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\nkot\Documents\eclipse-workspace\binaries\WordGame\dist\JAVA-project.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\nkot\Documents\eclipse-workspace\JAVA-project\wooden.jpg"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\nkot\Documents\eclipse-workspace\binaries\WordGame\dist\libs\JAVA-project-preloader.jar"; DestDir: "{app}\libs"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\nkot\Documents\eclipse-workspace\binaries\WordGame\dist\libs\org.hamcrest.core_1.3.0.v20180420-1519.jar"; DestDir: "{app}\libs"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\nkot\Documents\eclipse-workspace\JAVA-project\words\*"; DestDir: "{app}\words"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{commonprograms}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall skipifsilent

