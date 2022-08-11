<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <meta name="x-apple-disable-message-reformatting">
  <title></title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/x-icon" href="favicon.ico">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.gstatic.com">
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Work+Sans&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css"
    integrity="sha512-1PKOgIY59xJ8Co8+NE6FZ+LOAZKjy+KY8iq0G4B3CyeY6wYHN3yt9PW0XpSriVlkMXe40PTKnXrLnZ9+fkDaog=="
    crossorigin="anonymous" />


    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <style>
    table, td, div, h1, p {font-family: Arial, sans-serif;}
    body{
      font-family: "source sans pro",sans-serif;
    }
    .image-gallery{
   width: fit-content;
    max-width: 950px;
    margin: 0 auto;
    padding: 50px 20px;
    display: grid;
    grid-template-columns: repeat(auto-fit,minmax(250px,1fr));
    grid-auto-rows: 250px;
    grid-auto-flow: dense;
    grid-gap:20px
}
.image-gallery .image-box{
    position: relative;
    background-color: #d7d7d8;
    overflow: hidden;
}

.image-gallery .image-box:nth-child(7n+1){
    grid-column: span 2;
    grid-row: span 2;
}

.image-gallery .image-box img{
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: all 0.5s ease;
}

.image-gallery .image-box .overlay{
    position: absolute;
    top: 0;
    right: 0;
   bottom: 0;
   left: 0;
   background-color: #fafafaf2;
   display: flex;
   align-items: center;
   justify-content: center;
   opacity: 0;
   transition: all 0.5s ease;
   z-index: 1;
}

.image-gallery .image-box:hover .overlay{
    top: 20px;
    right: 20px;
    bottom: 20px;
    left: 20px;
    opacity: 1;
}

.image-gallery .image-box .details{
    text-align: center;
}

.image-gallery .image-box .details .title{
    margin-bottom: 8px;
    font-size: 24px;
    font-weight: 600;
    position: relative;
    top: -5px;
    opacity: 0;
    visibility: hidden;
    transition: all 0.3s ease;
}
.image-gallery .image-box:hover .details .title{
    top: 0px;
    opacity: 1;
    visibility: visible;
    transition: all 0.3s 0.2s ease;
}

.image-gallery .image-box .details .title a{
    color: #222222;
    text-decoration: none;
}

@media(max-width:768px){
    .image-gallery{
        grid-template-columns: repeat(auto-fit,minmax(250px,1fr));
        grid-auto-rows: 250px;
    }

    .image-gallery .image-box:nth-child(7n+1){
        grid-column: unset;
        grid-row: unset;
    }
}
.table{
    background-color: #32312f;
    font-family: sans-serif;
    width: 100%;
    height: 200px;
     padding-right: 200px;
    border-collapse: collapse;
}
.table-container{
    padding: 0 10%;
    margin: 40px auto 0;
    margin-right: 150px;
}
.heading{
    font-size: 40px;
    text-align: center;
    margin-bottom: 5px;
}
.table thead{
    background-color: #ee2828;
}
.table thead tr th{
    font-size: 20px;
    font-weight: 600;
    letter-spacing: 0.35px;
    color: #FFFFFF;
    opacity: 1;
    padding: 12px;
    text-align: center;
    vertical-align: top;
    border: 1px solid #dee2e685;
}
.table tbody tr td{
    font-size: 15px;
    font-weight: 400;
    letter-spacing: 0.35px;
    color: #f1f1f1;
    background-color: #3c3f44;
    padding: 8px;
    text-align: center;
    border: 1px solid #dee2e685;
}

  </style>
</head>
<body style="margin:0;padding:0;position: relative;">
  <table role="presentation" style="width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;">
    <tr>
      <td align="center" style="padding:0;">
        <table role="presentation" style="width:602px;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0;text-align:left;">
          <tr>
            <td align="center" style="padding:40px 0 30px 0;background:#18191a;">
              <a href=""><img src="https://i.postimg.cc/7ZvV4B2j/Free-Sample-By-Wix-1.jpg" alt="" width="750" style="height: 400px;;display:block;" /></a>
            </td>
          </tr>

          <tr>
            <td style="padding:36px 30px 42px 30px;">
              <table role="presentation" style="width:100%;border-collapse:collapse;border:0;border-spacing:0;">
                <tr>
                  <td style="padding:0 0 36px 0;color:#153643;">
                    <h1 style="font-size:29px;margin:0 0 20px 0;font-family:Arial,sans-serif;">Congratulations From Carnivry</h1>
                    <p style="margin:0 0 12px 0;font-size:24px;line-height:24px;font-family:Arial,sans-serif;">Let's celebrate a successfull event!! Looking forword to big party on the way! Continue breaking your own records! Congrats!</p>
                    <p style="margin:0;font-size:24px;line-height:24px;font-family:Arial,sans-serif;"><a href="http://www.example.com" style="color:#ee4c50;text-decoration:underline;">Learn More</a></p>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <div class="table-container">
           <h1 style="font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;">Hello ${consumerName}!!this was your booked event ticket details</h1>
            <table class="table">
              <thead>
                <tr>
                  <th>Event-Title & Descriptionn</th>
                  <th >Event-Dates <img src="https://i.postimg.cc/SsTkQc96/date.jpg" alt="" style="width: 20px;height: 20px;"></th>
                  <th>Event-Timings<img src="https://i.postimg.cc/zXLKrjhk/time.jpg" alt="" style="width: 20px;height: 20px;position: center;"></th>
                  <th>Venue-Details<img src="https://i.postimg.cc/7YT9VDKD/location.jpg" alt="" style="width: 20px;height: 20px;"></th>
                  <th>NoOfSeats</th>
                  <th>TotalPrice</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td data-label="Event-Title">${eventTitle}
                   [${eventDescription}]</td>
                  <td data-label="Event-Dates">${StartDate?date} to ${EndDate?date}</td>
                  <td data-label="Event-Timings">
                      ${StartTime} TO ${EndTime}
                  </td>
                  <#assign house=houseNumber!"null"/><#if house=="0"><td data-label="Venue-Details"> ${venueName},${Country}</td>
                  <#else>
                  <td data-label="Venue-Details">${venueName},${houseNumber},${Street},${LandMark},${city},${State},${Country},${Pincode}</td></#if>
                  <td data=label="NoOfSeats">${noOfSeats}</td>
                  <td data-label="TotalPrice">${totalTickerPrice}</td>
                </tr>
              </tbody>
            </table>
          <#assign house=houseNumber!"null"/><#if house=="0">
          <#else>
           <div class="table-container">
            <h1 style="font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;">Seat-Details</h1>
             <table class="table">
                   <thead>
                        <tr>
                           <th>SeatId</th>
                           <th>SeatCategory</th>
                           <th>SeatPrice</th>
                        </tr>
                   </thead>
                      <tbody>
                       <#list listOfSeats as boughtSeat>
                         <tr>
                            <td>${boughtSeat.seatId}</td>
                            <td>${boughtSeat.seatCategory}</td>
                             <td>${boughtSeat.seatPrice}</td>
                         </tr>
                       </#list>
                      </tbody>
             </table>
            </div>
          </#if>
         </div>

          <h3 style="padding-left: 200px;padding-top: 50px; color: #ee4c50;font-size: 25px;"><u>Events Organised By Carnivry</u></h3>
          <div class="image-gallery">

            <div class="image-box">
              <img class="image-grid-col-2 image-grid-row-2" src="https://i.postimg.cc/76JDw3nk/stand-up-logo-round-linear-logo-microphone-vector-27405019.jpg" alt="architecture" style="width:1000px ;height: 250px;">
               <div class="overlay">
                <div class="details">
                  <h3 class="title">
                    <a href="#">StandUp-Events</a>
                  </h3>
                </div>
               </div>
              </div>

               <div class="image-box">
                <img class="image-grid-col-2 image-grid-row-2" src="https://i.postimg.cc/rsSktqHZ/webinars.jpg"  alt="architecture" style="width:100% ;height: 250px;">
                 <div class="overlay">
                  <div class="details">
                    <h3  class="title">
                      <a href="#">Webinars</a>
                    </h3>
                  </div>
                 </div>
               </div>

                 <div class="image-box">
                  <img class="image-grid-col-2 image-grid-row-2" src="https://i.postimg.cc/8PmGpPMj/spiritual.jpg"  alt="architecture" style="width:100% ;height: 250px;">
                   <div class="overlay">
                    <div class="details">
                      <h3 class="title">
                        <a href="#">Spiritual-Events</a>
                      </h3>
                    </div>
                   </div>
                 </div>

                   <div class="image-box">
                    <img class="image-grid-col-2 image-grid-row-2" src="https://i.postimg.cc/9Qxj98x2/music-concert.jpg" alt="architecture" style="width:100% ;height: 250px;">
                     <div class="overlay">
                      <div class="details">
                        <h3 class="title">
                          <a href="#">Music-Events</a>
                        </h3>
                      </div>
                     </div>
                    </div>

                     <div class="image-box">
                      <img class="image-grid-col-2 image-grid-row-2" src="https://i.postimg.cc/Qx46k2rf/workshop.jpg" alt="architecture" style="width:100% ;height: 250px;">
                       <div class="overlay">
                        <div class="details">
                          <h3 class="title">
                            <a href="#">Workshops</a>
                          </h3>
                        </div>
                       </div>
                      </div>

                      <div class="image-box">
                        <img class="image-grid-col-2 image-grid-row-2" src="https://i.postimg.cc/L6nqyBkJ/exhibition.jpg" alt="architecture" style="width:100% ;height: 250px;">
                         <div class="overlay">
                          <div class="details">
                            <h3 class="title">
                              <a href="#">Exhibitions</a>
                            </h3>
                          </div>
                         </div>
                        </div>

           </div>

          <tr>
            <td style="padding:30px;background:#ee4c50;">
              <table role="presentation" style="width:100%;border-collapse:collapse;border:0;border-spacing:0;font-size:9px;font-family:Arial,sans-serif;">
                <tr>
                  <td style="padding:0;width:50%;" align="left">
                    <p style="margin:0;font-size:14px;line-height:16px;font-family:Arial,sans-serif;color:#ffffff;">
                      &reg; Carnivry, Carnivry 2022<br/><a href="http://www.example.com" style="color:#ffffff;text-decoration:underline;">Unsubscribe</a>
                    </p>
                  </td>
                  <td style="padding:0;width:50%;" align="right">
                    <table role="presentation" style="border-collapse:collapse;border:0;border-spacing:0;">
                      <tr>
                        <td style="padding:0 0 0 10px;width:38px;">
                          <a href="http://www.twitter.com/" style="color:#ffffff;"><img src="https://assets.codepen.io/210284/tw_1.png" alt="Twitter" width="38" style="height:auto;display:block;border:0;" /></a>
                        </td>
                        <td style="padding:0 0 0 10px;width:38px;">
                          <a href="http://www.facebook.com/" style="color:#ffffff;"><img src="https://assets.codepen.io/210284/fb_1.png" alt="Facebook" width="38" style="height:auto;display:block;border:0;" /></a>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
</html>