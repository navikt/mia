var WAIT_TIME;
var ledigestillinger;

module.exports = {
    before: (client) => {
        WAIT_TIME = client.globals.test_settings.timeout;
        ledigestillinger = client.init().page.ledigestillinger();
        ledigestillinger.navigate();
    },
    after: (client) => {
        client.end();
    },
    "oversikt, bransjer og graf skal være synlig": function () {
        ledigestillinger.expect.section('@oversikt').to.be.present.after(WAIT_TIME);
        ledigestillinger.expect.section('@bransjer').to.be.present.after(WAIT_TIME);
        ledigestillinger.expect.section('@statistikk').to.be.present.after(WAIT_TIME);
    }
};