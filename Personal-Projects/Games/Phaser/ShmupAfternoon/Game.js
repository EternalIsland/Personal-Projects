BasicGame.Game = function (game) {

    //	When a State is added to Phaser it automatically has the following properties set on it, even if they already exist:

    this.game;		//	a reference to the currently running game
    this.add;		//	used to add sprites, text, groups, etc
    this.camera;	//	a reference to the game camera
    this.cache;		//	the game cache
    this.input;		//	the global input manager (you can access this.input.keyboard, this.input.mouse, as well from it)
    this.load;		//	for preloading assets
    this.math;		//	lots of useful common math operations
    this.sound;		//	the sound manager - add a sound, play one, set-up markers, etc
    this.stage;		//	the game stage
    this.time;		//	the clock
    this.tweens;    //  the tween manager
    this.state;	    //	the state manager
    this.world;		//	the game world
    this.particles;	//	the particle manager
    this.physics;	//	the physics manager
    this.rnd;		//	the repeatable random number generator

    //	You can use any of these from any function within this State.
    //	But do consider them as being 'reserved words', i.e. don't create a property for your own game called "world" or you'll over-write the world reference.

};

BasicGame.Game.prototype = {

    preload: function () {
        this.load.image('sea', 'assets/seatile.png');
        this.load.image('bullet', 'assets/bullet.png');
        this.load.spritesheet('greenEnemy', 'assets/greenenemy.png', 33, 32);
        this.load.spritesheet('explosion', 'assets/explosion.png', 33, 32);
        this.load.spritesheet('player', 'assets/player.png', 66, 64);
    },

    create: function () {
        this.sea = this.add.tileSprite(0, 0, 1024, 768, 'sea');

        this.player = this.add.sprite(400, 650, 'player');
        this.player.anchor.setTo(0.5, 0.5);
        this.player.animations.add('fly', [0, 1, 2], 20, true);
        this.player.play('fly');
        this.physics.enable(this.player, Phaser.Physics.ARCADE);
        this.player.speed = 300;
        this.player.body.collideWorldBounds = true;

        this.enemy = this.add.sprite(512, 300, 'greenEnemy');
        this.enemy.animations.add('fly', [0, 1, 2], 20, true);
        this.enemy.play('fly');
        this.enemy.anchor.setTo(0.5, 0.5);
        this.physics.enable(this.enemy, Phaser.Physics.ARCADE);

        this.bullets = [];
        this.nextShotAt = 0;
        this.shotDelay = 200;

        this.cursors = this.input.keyboard.createCursorKeys();

        this.instructions = this.add.text(510, 600, 'Arrow keys to move, Z to shoot.\n Tapping/clicking does both simultaneously.');
        this.instructions.anchor.setTo(0.5, 0.5);
        this.instExpire = this.time.now + 7000;
    },

    update: function () {

        this.sea.tilePosition.y += 0.2;

        for (var i = 0; i < this.bullets.length; i++) {
            this.physics.arcade.overlap(this.bullets[i], this.enemy, this.enemyHit, null, this);
        }
        this.player.body.velocity.x = 0;
        this.player.body.velocity.y = 0;

        //keyboard controls
        if (this.cursors.left.isDown) {
            this.player.body.velocity.x = -this.player.speed;
        } else if (this.cursors.right.isDown) {
            this.player.body.velocity.x = this.player.speed;
        }

        if (this.cursors.up.isDown) {
            this.player.body.velocity.y = -this.player.speed;
        } else if (this.cursors.down.isDown) {
            this.player.body.velocity.y = this.player.speed;
        }

        //touch/mouse controls
        // stop moving once near pointer to stop "jittery" effect
        if (this.input.activePointer.isDown && this.physics.arcade.distanceToPointer(this.player) > 15) {
            this.physics.arcade.moveToPointer(this.player, this.player.speed);
        }

        if (this.input.keyboard.isDown(Phaser.Keyboard.Z) || this.input.activePointer.isDown) {
            this.fire();
        }

        if (this.instructions.exists && this.time.now > this.instExpire) {
            this.instructions.destroy();
        }
    },

    enemyHit: function (bullet, enemy) {
        bullet.kill();
        enemy.kill();

        var explosion = this.add.sprite(enemy.x, enemy.y, 'explosion');
        explosion.anchor.setTo(0.5, 0.5);
        explosion.animations.add('explode');
        explosion.play('explode', 15, false, true);
    },

    fire: function () {
        if (this.nextShotAt > this.time.now) {
            return;
        }

        this.nextShotAt = this.time.now + this.shotDelay;

        var bullet = this.add.sprite(this.player.x, this.player.y - 20, 'bullet');
        bullet.anchor.setTo(0.5, 0.5);
        this.physics.enable(bullet, Phaser.Physics.ARCADE);
        bullet.body.velocity.y = -500;
        this.bullets.push(bullet);
    },

    quitGame: function (pointer) {

        //	Here you should destroy anything you no longer need.
        //	Stop music, delete sprites, purge caches, free resources, all that good stuff.

        //	Then let's go back to the main menu.
        this.state.start('MainMenu');

    }

};
