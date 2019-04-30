function deleteById(element, url) {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");

    let id = element.id;

    $.ajax({
        type: "POST",
        url: url,
        data: {
            id: id
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },

        success: function (redirect) {
            window.location.replace(redirect)
        }
    });
}

function calculateTotal() {
    let myCurrencyRate = document.getElementById("myCurrency").value;
    let exchangeCurrency = document.getElementById("exchangeCurrency");
    let exchangeCurrencyRate = exchangeCurrency.value;
    let exchangeCurrencyCode = getSelectedText("exchangeCurrency").split(' -- ')[0];

    let amount = document.getElementById("amount").value;

    let totalInput = document.getElementById("calculatedJS");

    //do all the calculations here
    let total = ((myCurrencyRate / exchangeCurrencyRate) * amount).toFixed(2);

    totalInput.innerHTML = total + " " + exchangeCurrencyCode
}

function getSelectedText(elementId) {
    let elt = document.getElementById(elementId);

    if (elt.selectedIndex <= 0)
        return '';

    return elt.options[elt.selectedIndex].text;
}