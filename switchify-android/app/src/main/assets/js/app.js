(function(){

	var settings = {
		channel: 'gpio-galileo-control',
		publish_key: 'pub-c-0d652f8b-a119-48b8-b625-9953fdaf67bb',
		subscribe_key: 'sub-c-5b803346-6f22-11e5-b932-02ee2ddab7fe'
	};

	var pubnub = PUBNUB(settings);

	var light = document.getElementById('light');
	var fan = document.getElementById('fan');
	var lightLiving = document.getElementById('lightLiving');
	var lightPorch = document.getElementById('lightPorch');
	var fireplace = document.getElementById('fireplace');

	// pubnub.subscribe({
	// 	channel: settings.channel,
	// 	callback: function(m) {
	// 		if(m.temperature) {
	// 			document.querySelector('[data-temperature]').dataset.temperature = m.temperature;
	// 		}
	// 		if(m.humidity) {
	// 			document.querySelector('[data-humidity]').dataset.humidity = m.humidity;
	// 		}
	// 	}
	// })

	/* 
		Data settings:

		item: 'light'
		open: true | false
		
		item: 'fan'
		open: true | false

		LED

		item: 'light-*'
		brightness: 0 - 10

	*/

	function publishUpdate(data) {
		pubnub.publish({
			channel: settings.channel, 
			message: data
		});
	}

	// UI EVENTS

	switch_1.addEventListener('change', function(e){
		//publishUpdate({item: 'light', lightStatus: this.checked});
		$.ajax({
    		url: 'http://192.168.4.1/16/toggle',
   			dataType: 'jsonp',
    		complete : function(){
    		},
    		success: function(xml){
    		}
		});
		console.log ( '# Switch 1 Button was clicked' );
	}, false);

	switch_2.addEventListener('change', function(e){
		//publishUpdate({item: 'fan', fanStatus: this.checked});
		$.ajax({
    		url: 'http://192.168.4.1/2/toggle',
   			dataType: 'jsonp',
    		complete : function(){
    		},
    		success: function(xml){
    		}
		});
		console.log ( '# Switch 2 Button was clicked' );
	}, false);
})();
