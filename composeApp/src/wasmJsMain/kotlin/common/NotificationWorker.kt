package common

fun notificationWorker(title: String, message: String) {
    js(
        """
    var lastNotifiedString = localStorage.getItem("lastNotified");
    var lastNotified = lastNotifiedString ? new Date(lastNotifiedString) : null;
    var now = new Date();
    var oneDayFromNow = new Date().getTime() + (1 * 24 * 60 * 60 * 1000); // day * hour * min * sec * msec
    
    const options = {
    body: message,
    };
    
    if (lastNotified && lastNotified.getTime() < oneDayFromNow) {
    
    } else {
      notificationWorker();
      //stores last notification date in localStorage
      localStorage.setItem("lastNotified", now.toISOString());
    }
    
    function notificationWorker() {
      if (!("Notification" in window)) {
        alert("This browser does not support desktop notification");
      } else if (Notification.permission === "granted") {
        new Notification(title, options);
      } else if (Notification.permission !== "denied") {
        Notification.requestPermission().then(function(permission) {
          if (permission === "granted") {
            new Notification(title, options);
          } else {
            console.log('Permission denied by the user.');
          }
        });
      } else {
        console.log('Notifications are denied.');
      }
    }
  """
    )
}

