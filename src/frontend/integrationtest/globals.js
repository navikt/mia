const driver = require('./driver');
const { getNetworkIp } = require('./getNetworkIp');

module.exports = {
    default: {
        isLocal: true,
    },

    beforeEach: function(browser, done){
        if (!this.isLocal){
            getNetworkIp()
                .then(ip => browser.globals.launch_url = `http://${ip}:3000/`)
                .then(() => done())
                .catch(error => done(error))
        }
        else done();
    },

    before: function(done) {
        if(this.isLocal) driver.start();
        setTimeout(function() {
            done();
        }, 2000);

    },

    after: function(done) {
        if(this.isLocal) driver.stop();
        setTimeout(function() {
            done();
        }, 200);
    },
};
