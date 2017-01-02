exports.command = function(page, id, client, callback) {
    var self = this;
    page.api.element('id', id, function (response) {
            client.assert.ok(response.value.ELEMENT, 'checkbox response ok');
            client.elementIdSelected(response.value.ELEMENT, function (res) {
                client.verify.ok(!res.value, 'checkbox er avvalgt');
            });
        });
    this.execute(function(result){
            if(typeof callback === "function") {
                callback.call(self, result);
            }
        });

    return this;
};