$(document).ready(function() {
    $('#calculateTax').on('click', function() {
        const builtUpArea = $('#builtUpArea').val();
        const zoneClassification = $('#zoneClassification').val();
        const propertyDescription = $('#propertyDescription').val();

        // Example business logic for tax calculation
        let taxRate = 10;
        if (zoneClassification === '1') taxRate += 5;
        if (propertyDescription === 'RCC') taxRate += 2;

        const totalTax = builtUpArea * taxRate;
        $('#totalTax').val(totalTax);
    });

    $('#cancel').on('click', function() {
        window.location.href = '/';
    });
});
