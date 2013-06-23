var keyTime, keyStr = '', allOpts, lastElement;
var agt = navigator.userAgent.toLowerCase();
var is_gecko = (agt.indexOf("gecko") != -1);
var old = "";
var changed = false;

var winPreview = null;

function showStatus(text, visible) {
	var v = true;
	if (visible == undefined) {
		v = true;
	} else {
		v = visible;
	}

	if (v) {
		var statusWidth = 250;
		var statusHeight = 80;
		var wH = document.body.offsetHeight;
		var wW = document.body.offsetWidth;

		if (window.innerHeight) {
			wH = window.innerHeight;
			wW = window.innerWidth;
		}
		var wleft = (wW - statusWidth) / 2;
		var wtop = (wH - statusHeight) / 2;

		statusDiv = document.createElement('div');
		statusDiv.setAttribute("id", "statusDiv");
		statusDiv.style.position = "absolute";
		statusDiv.style.top = wtop + 'px';
		statusDiv.style.left = wleft + 'px';
		statusDiv.style.height = statusHeight + 'px';
		statusDiv.style.width = statusWidth + 'px';
		statusDiv.style.backgroundColor = '#CCCCCC';
		statusDiv.style.visibility = 'visible';
		statusDiv.style.display = 'block';
		statusDiv.style.borderBottom = '1px solid #000000';
		statusDiv.style.borderLeft = '1px solid #FFFFFF';
		statusDiv.style.borderRight = '1px solid #000000';
		statusDiv.style.borderTop = '1px solid #FFFFFF';
		statusDiv.style.padding = 5 + 'px;';
		statusDiv.style.zIndex = 0;
		statusDiv.innerHTML = "<br><div class='preloader'>" + text + "</div>";
		document.body.appendChild(statusDiv);
	} else {
		statusDiv = document.getElementById('statusDiv');
		document.body.removeChild(statusDiv);
		// if(statusDiv != null)
		// statusDiv.style.visibility = 'hidden';
	}

}

function clearInput() {
	// clear all form elements
	var theForm = document.forms[0];
	if (theForm != null) {
		var e = null;
		for ( var i = 0; i < theForm.elements.length; i++) {
			e = theForm.elements[i];

			if (e.type == 'button' || e.type == 'submit') {
			} else {
				if (e.options) {
					e.options.selectedIndex = -1;
				} else {
					e.value = '';
				}
			}

		}
	}
}

function setFocus(elID) {
	var el = document.getElementById(elID);
	if (el == null) {
		el = document.getElementsByName(elID)[0];
	}
	if (el != null) {
		el.focus();
	}
}

function populate(srcEvent) {
	changed = false;
	var element = (srcEvent) ? ((srcEvent.target) ? srcEvent.target
			: srcEvent.srcElement) : window.event.srcElement;
	if (lastElement != element) {
		allOpts = new Array();
		for ( var i = 0; i < element.options.length; i++) {
			allOpts[i] = element.options[i].text.toLowerCase();
		}
		lastElement = element;
		old = element.value;
	}
}

function setSelection(srcEvent) {
	var myEvent = (srcEvent) ? srcEvent : window.event;
	var element = (myEvent.target) ? myEvent.target : myEvent.srcElement;
	var keyCode = myEvent.keyCode;
	// messy JS keycodes force me to preprocess. Note: I use a US keyboard,
	// other keyboards may vary?
	if ((keyCode > 47 && keyCode < 58)
			|| (keyCode > 64 && keyCode < 91 || keyCode == 32)) {
		; // space or alphanumerical characters, leave them alone
	} else if (keyCode > 95 && keyCode < 106) {
		keyCode -= 48; // keypad numbers
	} else if (keyCode > 105 && keyCode < 112) {
		keyCode -= 64; // keypad '+', '-', '/', '*', '.'
	} else if (keyCode > 187 && keyCode < 192) {
		keyCode -= 144; // '/', '.', ',', '-'
	} else if (keyCode > 218 && keyCode < 222) {
		keyCode -= 128; // '\', '[', ']'
	} else {
		switch (keyCode) {
		case 187:
			keyCode = 61;
			break; // '='
		case 222:
			keyCode = 39;
			break; // '''
		case 192:
			keyCode = 96;
			break; // '`'
		case 186:
			keyCode = 59;
			break; // ';'
		default:
			return; // do not process non printable characters (unfortunately
			// backspace cannot be supported because browsers like IE
			// interpret backspace as go back a page in history)
		}
	}
	var currentKey = String.fromCharCode(keyCode).toLowerCase();
	var idx, currentSIdx = element.selectedIndex;
	var newTime = new Date().getTime();
	if (keyTime != null && newTime - keyTime < 500) // do type-ahead if two keys
	// were pressed within 500
	// milliseconds (0.5 second,
	// one can change this value
	// for customization)
	{
		keyStr += currentKey;
		idx = findIdx();
		if (idx == -1) {
			return; // not found, keep current selection then (leave the
			// incorrect keyStr alone)
		}
	} else // unfortunately we seem to have to handle default browser behavior
	// too
	{
		keyStr = currentKey;
		// behavior should be: if next option is available and begins with the
		// same character, select the next option
		// when there is either no more option, or no more option that begins
		// with the same character as the current option,
		// then select the first option that starts with the currentKey
		idx = currentSIdx + 1;
		if (idx >= allOpts.length || allOpts[idx].length == 0
				|| allOpts[idx].charAt(0) != keyStr) {
			idx = findIdx();
		}
	}
	if (idx >= 0) // if keyStr is found in an option, select the option
	{
		element.options[currentSIdx].selected = false;
		// gecko-based browsers have a very strange bug that strikes when user
		// presses
		// the same character multiple times (like 'AAA', 'BBBB'), which could
		// be "fixed"
		// in a strange way too (actually the idx > 0 test is not even
		// necessary!)
		// first make a pattern to check if it's same character multiple times
		var pattern = new RegExp('^' + keyStr.charAt(0) + '+$', "i");
		if (is_gecko && pattern.test(keyStr) && idx > 0) {
			element.options[idx - 1].selected = true;
		} else {
			element.options[idx].selected = true;
			// element.value = element.options[idx].value;
			if (old != element.value) {
				changed = true;
			} else {
				changed = false;
			}
		}
	}
	keyTime = newTime;
}
function findIdx() {
	// full scan to find the smallest idx that match string keyStr
	// (case-insensitive)
	var len = keyStr.length;
	for ( var i = 0; i < allOpts.length; i++) {
		if (allOpts[i].length >= len && allOpts[i].substring(0, len) == keyStr) {
			return i;
		}
	}
	return -1;
}

function hasChanged() {
	return changed;
}

function checkChange(el, strAction) {
	if (hasChanged()) {
		if (strAction != undefined && strAction != null) {
			el.form.action = strAction;
		}
		el.form.submit();
	}
}

function showPreview(strImagePath) {
	winPreview = window
			.open(strImagePath, 'winPreview',
					'location=no,menubar=no,scrollbars=yes,width=800,height=600,resizable=yes');
}

function checkKey(e) {
	// alert(e);
	if (!is_gecko) {
		if (event.keyCode == 13) {
			for ( var i = 0; i < document.forms.length; i++) {
				if (document.forms[i].actionMethode) {
					showStatus('Searching...', true);
					document.forms[i].actionMethode.value = "suchen";
					document.forms[i].submit();
					break;
				}
			}
		}
	}

	else {
		var ev = e.which;
		if (ev == 13) {
			for ( var i = 0; i < document.forms.length; i++) {
				if (document.forms[i].actionMethode) {
					showStatus('Searching...', true);
					document.forms[i].actionMethode.value = "suchen";
					document.forms[i].submit();
					break;
				}
			}
		}
	}
}
