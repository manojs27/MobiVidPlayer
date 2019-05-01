# MobiVidPlayer

1. Firebase Signup and SignIn Authentication.
2. onSuccessful Login into Application -> Videos List are fetched and displayed using RecyclerView.
3. Glide Library is used for download and display the thumbnail image of videos. 
4. Selecting Videos , we are navigated to player screen where selected video plays which contains recommended videos below.
5. Exo Player is used to play videos from video url .
6. If user navigates back from current video, Video of already played video will starts from last seekTime or Last video paused duration.
7. Continue playing other recommended videos in the list once current video ends.

Use MVP Pattern as architecture ,Volley is used for network calls
