exports.command = function(page, feilmeldingstekst, callback) {
    var self = this;
    page.expect.element(".skjema-feilmelding").text.to.contain(feilmeldingstekst);
    this.execute(function(result){
        if(typeof callback === "function") {
            callback.call(self, result);
        }
    });

    return this;
};