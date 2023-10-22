# Capstone_project
Requirements
• JDK 1.8 (or higher), Maven (pom.xml)
• Junit4 or 5, or TestNG
• WebDriver (org.seleniumhq.selenium:selenium-java) for UI
• PageObject or PageFactory, Webdriver Singleton
• Packages architecture for storing the classes: web-driver, page-objects, models (domains), utils, steps, etc.

Part 1. UI test scripts

UI 1- Test Login form with empty credentials
1. Type any credentials.
2. Clear the inputs.
3. Check the error messages:
o Please enter your Spotify username or email address.
o Please enter your password.

UI 2- Test Login form with incorrect credentials 
1. Type any incorrect credentials.
2. Click the Log In button.
3. Check the error message:
a. Incorrect username or password

UI 3 - Test Login form with correct credentials
1. Type correct credentials.
2. Click the Log In button.
3. Check that Profile Name is correct

UI 4 - Test Create playlist
1. Log in with correct credentials.
2. Click Create playlist.
3. Check that playlist Name was added to the lis

UI 5- Test Edit details of playlist
1. Log in with correct credentials.
2. Click Create a new playlist.
3. Select Edit details.
4. Type new Name and click Save.
5. Check that name was changed.

UI 6 - Test Search and Add to playlist
1. Log in with correct credentials.
2. Click Create a new playlist.
3. Click Search menu.
4. Type in search input “Whitney Elizabeth Houston”.
5. Click Songs filter.
6. Select Add to playlist and select new created playlist from list for add new song.
7. Click the playlist where the song is added.
8. Check that the song was added

UI 7- Test Remove song from playlist
1. Log in with correct credentials.
2. Click Create a new playlist.
3. Click Search menu.
4. Type in search input “Whitney Houston”.
5. Click Songs filter.
6. Select Add to playlist and select newly created playlist from list for add new song.
7. Click the playlist where the song is added.
8. Select Remove from this playlist.
9. Check that song was removed from playlist

UI 8- Test Delete playlist
1. Log in with correct credentials.
2. Click Create Playlist.
3. Select Delete playlist.
4. Click the DELETE button.
5. Check that playlist was removed from your library
