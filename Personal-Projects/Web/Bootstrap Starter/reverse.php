<?php
inlude('init.php');

if (isset($_POST['name'])) {
	$name = mysql_real_escape_string(htmlentities($_POST['name']));

	$update = mysql_query("UPDATE 'users' SET 'name' = '$name' WHERE 'user_id' =" . $_SESSION['user-id']);

	if ($update === true) {
		echo "string";
		'Setting have been updated.';
	} else {
		echo 'There was an error.';
	}
}
?>